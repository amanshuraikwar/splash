# Splash (No Longer Maintained)
<b>Splash</b> is an app to browse photos from various sources like <b>Unsplash</b>. Due to it's initial stage of development it 
fetches photos from <b>Unsplash</b> only. In future <b>Splash</b> will fetch photos from various sources. Through <b>Splash</b> 
you can download photos and bookmark photos, collections and photographers in an offline list. <b>Splash</b> in no way tries to 
replace the official clients of the respective sources, it merely provides an alternative.

The design language used in <b>Splash</b> is based on Google's marterial design and it tries to be minimal wherever 
possible.

## Design

<div align="center">
<img width="240" src="https://amanshuraikwar.github.io/assets/splash/ss-home-1.jpg">
</div>

<div align="center">
<img width="240" src="https://amanshuraikwar.github.io/assets/splash/ss-home-2.jpg">
</div>

<div align="center">
<img width="240" src="https://amanshuraikwar.github.io/assets/splash/ss-photo-desc-1.jpg">
</div>

<div align="center">
<img width="240" src="https://amanshuraikwar.github.io/assets/splash/ss-collections-1.jpg">
</div>

<div align="center">
<img width="240" src="https://amanshuraikwar.github.io/assets/splash/ss-collection-desc-1.jpg">
</div>

<div align="center">
<img width="240" src="https://amanshuraikwar.github.io/assets/splash/ss-collection-desc-2.jpg">
</div>

<div align="center">
<img width="240" src="https://amanshuraikwar.github.io/assets/splash/ss-user-desc-1.jpg">
</div>

<div align="center">
<img width="240" src="https://amanshuraikwar.github.io/assets/splash/ss-user-desc-2.jpg">
</div>

<div align="center">
<img width="240" src="https://amanshuraikwar.github.io/assets/splash/ss-search-1.jpg">
</div>

<div align="center">
<img width="240" src="https://amanshuraikwar.github.io/assets/splash/ss-search-2.jpg">
</div>

<div align="center">
<img width="240" src="https://amanshuraikwar.github.io/assets/splash/ss-downloads.jpg">
</div>

## Build it
This public repository does not include APK binaries or API credentials.
To run the app against Unsplash, provide an access key outside Git:

```sh
UNSPLASH_ACCESS_KEY=your_access_key ./gradlew :app:assembleDebug
```

You can also put `UNSPLASH_ACCESS_KEY=your_access_key` in untracked `local.properties`.

## ADB over Wi-Fi

For the USB-assisted ADB TCP/IP workflow, use the maintained helper script:

```sh
./scripts/adbwificonnect.sh
```

Use `--device SERIAL` for a specific USB-connected device, `--all` for every
USB-connected device, or `--dry-run` to inspect the commands first. Android 11
and newer can also use Wireless debugging with `adb pair` without a USB cable.

To run it by name from any working directory, install it on your `PATH` once:

```sh
mkdir -p "$HOME/.local/bin"
ln -sf "/path/to/splash/scripts/adbwificonnect.sh" "$HOME/.local/bin/adbwificonnect"
export PATH="$HOME/.local/bin:$PATH"
```

## Please
> ### Please don't over use the app as the Api has a limit.
