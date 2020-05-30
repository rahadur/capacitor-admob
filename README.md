<p align="center">
  <img width="50%" height="auto" src="https://vignette.wikia.nocookie.net/logopedia/images/2/23/Admob12.png/revision/latest?cb=20190109132636">
</p>

# [Capacitor AdMob](https://github.com/rahadur/capacitor-admob) 💸

Capacitor AdMob is a native AdMob implementation for IOS & Android. Now you can use this package as a [Ionic Capacitor](https://capacitor.ionicframework.com) Plugin in your App.

## Changelog:

### [v1.0.0](https://github.com/rahadur/capacitor-admob/tree/v1.0.0)
- Fixed Androidx CoordinatorLayout **[#35](https://github.com/rahadur/capacitor-admob/issues/35)**
- Fixed Banner and interstitial share the same Event Listeners **[#40](https://github.com/rahadur/capacitor-admob/issues/40)**
- Fixed AdMob error in Mobile after build **[#48](https://github.com/rahadur/capacitor-admob/issues/48)**
- Migrate Demo App to Capacitor 2.1.2

#### [Previous Release](./CHANGELOG.md)


## Supported Platform

- [x] iOS
- [x] Android

## Other Plugins:

| Plugins                                             | Android | iOS | Electron | PWA |
| :-------------------------------------------------- | :------ | :-- | :------- | :-- |
| [MoPub](https://github.com/rahadur/capacitor-mopub) | ✅      | ❌  | ❌       | ❌  |

# 💰 For a Few Dollars More

Thanks for considering donate.

If this plugin help you to earn few dollars, please don't forget to share your profit, because **"Sharing is Caring"**. Your donation will inspire me a lot to give more time on open-source project & help you by creating more useful plugins.

| Methond   | Type    | Amount | Link                                                                                             |
| :-------- | :------ | :----- | :----------------------------------------------------------------------------------------------- |
| Paypal.me | Once    | Any    | [paypal.me](https://www.paypal.me/xplatform)                                                     |
| Paypal    | Monthly | \$5    | [paypal.com](https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=76FEY9XUD9A98) |
| Paypal    | Monthly | \$10   | [paypal.com](https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=HUQLAF8W5M2FA) |
| Paypal    | Monthly | \$25   | [paypal.com](https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=RWELCRVN64GHC) |
| Paypal    | Monthly | \$50   | [paypal.com](https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=7C4EEH6ELENKA) |

## AdMob Demo App

Download Demo App from **[Here](https://github.com/rahadur/capacitor-admob/tree/master/admob-demo)**

## Screenshot

### iOS

| Basic Banner AD                                                  | Interstitial AD                                                                    | Interstitial Video AD                                                                               | Reward Video AD                                                               |
| ---------------------------------------------------------------- | ---------------------------------------------------------------------------------- | --------------------------------------------------------------------------------------------------- | ----------------------------------------------------------------------------- |
| ![Alt text](/screenshort/ios/banner_ad.png?raw=true "Banner AD") | ![Intertitial AD ](/screenshort/ios/intertitial_ad.png?raw=true "Intertitial AD ") | ![Intertitial Video AD](/screenshort/ios/intertitial_video_ad.png?raw=true "Intertitial Video AD ") | ![Reward Video AD](/screenshort/ios/reward_ad.png?raw=true "Reward Video AD") |

### Android

| Basic Banner AD                                                      | Interstitial AD                                                                        | Interstitial Video AD                                                                                   | Reward Video AD                                                                   |
| -------------------------------------------------------------------- | -------------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------- | --------------------------------------------------------------------------------- |
| ![Alt text](/screenshort/android/banner_ad.png?raw=true "Banner AD") | ![Intertitial AD ](/screenshort/android/intertitial_ad.png?raw=true "Intertitial AD ") | ![Intertitial Video AD](/screenshort/android/intertitial_video_ad.png?raw=true "Intertitial Video AD ") | ![Reward Video AD](/screenshort/android/reward_ad.png?raw=true "Reward Video AD") |

```console
cd admob-demo

npm install

ionic build

npx cap copy

npx cap sync

npx cap update

npx cap open android

============== Or just use this command ===========

npm install & ionic build & npx cap copy & npx cap sync & npx cap update & npx cap open android
```

## Installation

Use **AdMob** plugins in your app.

```console
 npm install --save capacitor-admob
```

## iOS

### Update **Info.plist**

Open your **App/App/Info.plist** file and add this `plist value` line at the right spot (and replace the value by the actual App ID of your app!):

```xml
<!-- this two line needs to be added -->

<key>GADIsAdManagerApp</key>
<true/>

<key>GADApplicationIdentifier</key>
<!-- replace this value with your App ID key-->
<string>ca-app-pub-6564742920318187~7217030993</string>

```

## Android

### Update Manifest

Open your **android/app/src/Android/AndroidManifest.xml** file and add this `meta-data` line at the right spot (and replace the value by the actual App ID of your app!):

```xml
<application>
  <!-- this line needs to be added (replace the value!) -->
  <meta-data android:name="com.google.android.gms.ads.APPLICATION_ID" android:value="ca-app-pub-3940256099942544~3347511713" />
  <activity></activity>
</application>

```

### Register AdMob to Capacitor Android

Open your Ionic Capacitor App in Android Studio, Now open **MainActivity.java** of your app and Register AdMob to Capacitor Plugins.

```java
// Other imports...
import app.xplatform.capacitor.plugins.AdMob;

public class MainActivity extends BridgeActivity {
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    this.init(savedInstanceState, new ArrayList<Class<? extends Plugin>>() {{

      add(AdMob.class);  // Add AdMob as a Capacitor Plugin

    }});
  }
}
```

## 📌 Initialize AdMob

Open your Ionic app **app.component.ts** file and add this folloing code.

```typescript
import { Plugins } from "@capacitor/core";
// import { initialize } from 'capacitor-admob'; No longar required

const { AdMob } = Plugins;

@Component({
  selector: "app-root",
  templateUrl: "app.component.html",
  styleUrls: ["app.component.scss"]
})
export class AppComponent {
  constructor() {
    // Initialize AdMob for your Application
    AdMob.initialize("YOUR APPID");
  }
}
```

## 📌 BANNER

### showBanner(options: AdOptions): Promise<{ value: boolean }>

```typescript
import { Plugins } from "@capacitor/core";
import { AdOptions, AdSize, AdPosition } from "capacitor-admob";

const { AdMob } = Plugins;

@Component({
  selector: "admob",
  templateUrl: "admob.component.html",
  styleUrls: ["admob.component.scss"]
})
export class AdMobComponent {
  options: AdOptions = {
    adId: "YOUR ADID",
    adSize: AdSize.BANNER,
    position: AdPosition.BOTTOM_CENTER
  };

  constructor() {
    // Show Banner Ad
    AdMob.showBanner(this.options).then(
      value => {
        console.log(value); // true
      },
      error => {
        console.error(error); // show error
      }
    );

    // Subscibe Banner Event Listener
    AdMob.addListener("onAdLoaded", (info: boolean) => {
      console.log("Banner Ad Loaded");
    });
  }
}
```

### hideBanner(): Promise<{ value: boolean }>

```typescript
// Hide the banner, remove it from screen, but can show it later

AdMob.hideBanner().then(
  value => {
    console.log(value); // true
  },
  error => {
    console.err(error); // show error
  }
);
```

### resumeBanner(): Promise<{ value: boolean }>

```typescript
// Resume the banner, show it after hide

AdMob.resumeBanner().then(
  value => {
    console.log(value); // true
  },
  error => {
    console.error(error); // show error
  }
);
```

### removeBanner(): Promise<{ value: boolean }>

```typescript
// Destroy the banner, remove it from screen.

AdMob.removeBanner().then(
  value => {
    console.log(value); // true
  },
  error => {
    console.error(error); // show error
  }
);
```

### Event Listener

This following Event Listener can be called in **Banner AD**.

```typescript
addListener(eventName: 'onAdLoaded', listenerFunc: (info: any) => void): PluginListenerHandle;

addListener(eventName: 'onAdFailedToLoad', listenerFunc: (info: any) => void): PluginListenerHandle;

addListener(eventName: 'onAdOpened', listenerFunc: (info: any) => void): PluginListenerHandle;

addListener(eventName: 'onAdClosed', listenerFunc: (info: any) => void): PluginListenerHandle;
```

## 📌 INTERSTITIAL

### prepareInterstitial(options: AdOptions): Promise<{ value: boolean }>

```typescript
import { Plugins } from "@capacitor/core";
import { AdOptions } from "capacitor-admob";

const { AdMob } = Plugins;

@Component({
  selector: "admob",
  templateUrl: "admob.component.html",
  styleUrls: ["admob.component.scss"]
})
export class AppComponent {
  options: AdOptions = {
    adId: "Your AD_Id",
    adSize: AdSize.SMART_BANNER,
    position: AdPosition.BOTTOM_CENTER,
    hasTabBar: false, // make it true if you have TabBar Layout.
    tabBarHeight: 56 // you can assign custom margin in pixel default is 56
  };

  constructor() {
    // Prepare interstitial banner
    AdMob.prepareInterstitial(this.options).then(
      value => {
        console.log(value); // true
      },
      error => {
        console.error(error); // show error
      }
    );

    // Subscibe Banner Event Listener
    AdMob.addListener("onInterstitialAdLoaded", (info: boolean) => {
      // You can call showInterstitial() here or anytime you want.

      console.log("Interstitial Ad Loaded");
    });
  }
}
```

### showInterstitial(): Promise<{ value: boolean }>

```typescript
// Show interstitial ad when it’s ready

AdMob.showInterstitial().then(
  value => {
    console.log(value); // true
  },
  error => {
    console.error(error); // show error
  }
);
```

### Event Listener

This following Event Listener can be called in **Interstitial AD**

```typescript
 addListener(eventName: 'onInterstitialAdLoaded', listenerFunc: (info: any) => void): PluginListenerHandle;

addListener(eventName: 'onInterstitialAdFailedToLoad', listenerFunc: (info: any) => void): PluginListenerHandle;

addListener(eventName: 'onInterstitialAdOpened', listenerFunc: (info: any) => void): PluginListenerHandle;

addListener(eventName: 'onInterstitialAdLeftApplication', listenerFunc: (info: any) => void): PluginListenerHandle;

addListener(eventName: 'onInterstitialAdClosed', listenerFunc: (info: any) => void): PluginListenerHandle;
```

## 📌 RewardVideo

### prepareRewardVideoAd(options: AdOptions): Promise<{ value: boolean }>

```typescript
import { Plugins } from "@capacitor/core";
import { AdOptions } from "capacitor-admob";

const { AdMob } = Plugins;

@Component({
  selector: "admob",
  templateUrl: "admob.component.html",
  styleUrls: ["admob.component.scss"]
})
export class AAdMobComponent {
  options: AdOptions = {
    adId: "YOUR ADID"
  };

  constructor() {
    // Prepare ReWardVideo
    AdMob.prepareRewardVideoAd(this.options).then(
      value => {
        console.log(value); // true
      },
      error => {
        console.error(error); // show error
      }
    );

    // Subscibe ReWardVideo Event Listener
    AdMob.addListener("onRewardedVideoAdLoaded", (info: boolean) => {
      // You can call showRewardVideoAd() here or anytime you want.
      console.log("RewardedVideoAd Loaded");
    });
  }
}
```

### showRewardVideoAd(): Promise<{ value: boolean }>

```typescript
// Show a RewardVideo AD

AdMob.showRewardVideoAd().then(
  value => {
    console.log(value); // true
  },
  error => {
    console.error(error); // show error
  }
);
```

### pauseRewardedVideo(): Promise<{ value: boolean }>

```typescript
// Pause a RewardVideo AD

AdMob.pauseRewardedVideo().then(
  value => {
    console.log(value); // true
  },
  error => {
    console.error(error); // show error
  }
);
```

### resumeRewardedVideo(): Promise<{ value: boolean }>

```typescript
// Resume a RewardVideo AD

AdMob.resumeRewardedVideo().then(
  value => {
    console.log(value); // true
  },
  error => {
    console.error(error); // show error
  }
);
```

### stopRewardedVideo(): Promise<{ value: boolean }>

```typescript
// Stop a RewardVideo AD

AdMob.stopRewardedVideo().then(
  value => {
    console.log(value); // true
  },
  error => {
    console.error(error); // show error
  }
);
```

### Event Listener

This following Event Listener can be called in **RewardedVideo**

```typescript
addListener(eventName: 'onRewardedVideoAdLoaded', listenerFunc: (info: any) => void): PluginListenerHandle;

addListener(eventName: 'onRewardedVideoAdOpened', listenerFunc: (info: any) => void): PluginListenerHandle;

addListener(eventName: 'onRewardedVideoStarted', listenerFunc: (info: any) => void): PluginListenerHandle;

addListener(eventName: 'onRewardedVideoAdClosed', listenerFunc: (info: any) => void): PluginListenerHandle;

addListener(eventName: 'onRewarded', listenerFunc: (info: any) => void): PluginListenerHandle;

addListener(eventName: 'onRewardedVideoAdLeftApplication', listenerFunc: (info: any) => void): PluginListenerHandle;

addListener(eventName: 'onRewardedVideoAdFailedToLoad', listenerFunc: (info: any) => void): PluginListenerHandle;

addListener(eventName: 'onRewardedVideoCompleted', listenerFunc: (info: any) => void): PluginListenerHandle;
```

# API

### 📌 AdOptions

```typescript
interface AdOptions {
  adId: string;
  adSize?: AdSize;
  position?: AdPosition;
  hasTabBar?: boolean; // optional: default false
  tabBarHeight?: number; // set cutom height in pixal default is 56
  userId?: string; // RewardedVideo ONLY, Optional user ID useful when using SSV// Height in Pixal
}
```

### 📌 AdSize

```typescript
enum AdSize {
  BANNER = "BANNER",

  FLUID = "FLUID",

  FULL_BANNER = "FULL_BANNER",

  LARGE_BANNER = "LARGE_BANNER",

  LEADERBOARD = "LEADERBOARD",

  MEDIUM_RECTANGLE = "MEDIUM_RECTANGLE",

  SMART_BANNER = "SMART_BANNER",

  CUSTOM = "CUSTOM"
}
```

### 📌 AdPosition

```typescript
enum AdPosition {
  TOP_CENTER = "TOP_CENTER",

  CENTER = "CENTER",

  BOTTOM_CENTER = "BOTTOM_CENTER"
}
```

## Contributing

- 🌟 Star this repository
- 📋 Open issue for feature requests

## Roadmap

- [Capacitor Plugins](https://capacitor.ionicframework.com/docs/plugins/)

- [IOS](https://capacitor.ionicframework.com/docs/plugins/ios/)

- [Android](https://capacitor.ionicframework.com/docs/plugins/android/)

## License

```
The MIT License

Copyright (c) 2010-2018 Google, Inc. http://angularjs.org

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
```
