#!/usr/bin/env bash

# Connect one or more USB-connected Android devices over ADB TCP/IP.
# Android 11+ devices may use Wireless debugging and `adb pair` instead;
# this script is for the USB-assisted TCP/IP workflow.

set -Eeuo pipefail

readonly DEFAULT_PORT="5555"
readonly MAX_WAIT_SECONDS="10"
readonly SCRIPT_DIRECTORY="$(cd -- "$(dirname -- "${BASH_SOURCE[0]}")" && pwd)"

port="$DEFAULT_PORT"
selected_device=""
connect_all=false
dry_run=false

usage() {
    cat <<'EOF'
Usage: adbwificonnect.sh [options]

Connect USB-connected Android devices over ADB Wi-Fi.

Options:
  -d, --device SERIAL  Connect only the device with this serial.
  -a, --all            Connect every USB-connected device.
  -p, --port PORT      Use PORT instead of 5555.
      --dry-run        Print the planned commands without enabling TCP/IP.
  -h, --help           Show this help.

Examples:
  ./scripts/adbwificonnect.sh
  ./scripts/adbwificonnect.sh --device R58M123456A
  ./scripts/adbwificonnect.sh --all --port 5555

Install it as a global command:
  mkdir -p "$HOME/.local/bin"
  ln -sf "/path/to/splash/scripts/adbwificonnect.sh" "$HOME/.local/bin/adbwificonnect"
  export PATH="$HOME/.local/bin:$PATH"

For Android 11 and newer, Wireless debugging with `adb pair HOST:PORT`
is preferred when the device is not connected over USB.
EOF
}

die() {
    printf 'error: %s\n' "$1" >&2
    exit 1
}

is_positive_integer() {
    [[ "$1" =~ ^[1-9][0-9]*$ ]]
}

find_adb() {
    if [[ -n "${ADB_BIN:-}" ]]; then
        [[ -x "$ADB_BIN" ]] || die "ADB_BIN is not executable: $ADB_BIN"
        printf '%s\n' "$ADB_BIN"
        return
    fi

    if command -v adb >/dev/null 2>&1; then
        command -v adb
        return
    fi

    local candidate
    for candidate in \
        "$SCRIPT_DIRECTORY/adb" \
        "${ANDROID_HOME:-}/platform-tools/adb" \
        "${ANDROID_SDK_ROOT:-}/platform-tools/adb" \
        "${HOME:-}/Library/Android/sdk/platform-tools/adb"; do
        if [[ -x "$candidate" ]]; then
            printf '%s\n' "$candidate"
            return
        fi
    done

    die "adb was not found; install platform-tools or set ADB_BIN"
}

adb_bin="$(find_adb)"

get_usb_devices() {
    local serial state
    while read -r serial state _; do
        [[ -n "${serial:-}" ]] || continue
        [[ "${serial:0:1}" != "*" ]] || continue
        [[ "$serial" != "List" ]] || continue
        [[ "${state:-}" == "device" ]] || continue
        [[ "$serial" != *:* ]] || continue
        printf '%s\n' "$serial"
    done < <("$adb_bin" devices)
}

get_device_ip() {
    local device="$1"
    local ip

    # `ip -o` is available on current Android releases and does not depend on
    # the legacy `ifconfig wlan0` output removed from newer Android versions.
    ip="$("$adb_bin" -s "$device" shell ip -o -4 addr show scope global 2>/dev/null \
        | awk '$2 ~ /^(wlan|wifi|swlan|eth)/ { split($4, address, "/"); print address[1]; exit }' \
        | tr -d '\r')"

    if [[ -z "$ip" ]]; then
        # Keep support for older devices whose toybox does not support `ip -o`.
        ip="$("$adb_bin" -s "$device" shell ifconfig wlan0 2>/dev/null \
            | sed -n -E 's/.*inet (addr:)?([0-9]+(\.[0-9]+){3}).*/\2/p' \
            | head -n 1 \
            | tr -d '\r')"
    fi

    [[ -n "$ip" ]] || die "could not determine the Wi-Fi IPv4 address for $device"
    printf '%s\n' "$ip"
}

wait_for_connection() {
    local target="$1"
    local attempt=1

    while (( attempt <= MAX_WAIT_SECONDS )); do
        if "$adb_bin" connect "$target" >/dev/null 2>&1; then
            return 0
        fi
        sleep 1
        attempt=$((attempt + 1))
    done

    return 1
}

connect_device() {
    local device="$1"
    local ip target

    ip="$(get_device_ip "$device")"
    target="$ip:$port"

    printf 'device %s -> %s\n' "$device" "$target"

    if "$dry_run"; then
        printf '  %q -s %q tcpip %q\n' "$adb_bin" "$device" "$port"
        printf '  %q connect %q\n' "$adb_bin" "$target"
        return 0
    fi

    "$adb_bin" -s "$device" tcpip "$port"
    printf 'waiting for ADB TCP/IP daemon...\n'

    if ! wait_for_connection "$target"; then
        die "failed to connect to $target; check that both devices are on the same network"
    fi

    printf 'connected to %s\n' "$target"
}

while (($# > 0)); do
    case "$1" in
        -d|--device)
            (($# >= 2)) || die "$1 requires a serial"
            selected_device="$2"
            shift 2
            ;;
        -a|--all)
            connect_all=true
            shift
            ;;
        -p|--port)
            (($# >= 2)) || die "$1 requires a port"
            port="$2"
            shift 2
            ;;
        --dry-run)
            dry_run=true
            shift
            ;;
        -h|--help)
            usage
            exit 0
            ;;
        *)
            die "unsupported option: $1"
            ;;
    esac
done

is_positive_integer "$port" || die "port must be a positive integer: $port"
[[ -z "$selected_device" || "$connect_all" == false ]] || die "--device and --all cannot be combined"

devices=()
while IFS= read -r device; do
    [[ -n "$device" ]] && devices+=("$device")
done < <(get_usb_devices)

if [[ -n "$selected_device" ]]; then
    printf '%s\n' "${devices[@]}" | grep -Fqx "$selected_device" \
        || die "USB-connected device not found: $selected_device"
    connect_device "$selected_device"
elif "$connect_all"; then
    ((${#devices[@]} > 0)) || die "no authorized USB-connected devices found"
    for device in "${devices[@]}"; do
        connect_device "$device"
    done
elif ((${#devices[@]} == 0)); then
    die "no authorized USB-connected devices found; Android 11+ can use Wireless debugging with adb pair"
elif ((${#devices[@]} == 1)); then
    connect_device "${devices[0]}"
else
    printf 'Available USB-connected devices:\n'
    for index in "${!devices[@]}"; do
        printf '  [%s] %s\n' "$index" "${devices[index]}"
    done
    read -r -p 'Choose device: ' choice
    is_positive_integer "$choice" || [[ "$choice" == "0" ]] \
        || die "device choice must be a number"
    ((choice < ${#devices[@]})) || die "device choice is out of range"
    connect_device "${devices[choice]}"
fi
