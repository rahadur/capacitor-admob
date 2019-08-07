import { Component } from '@angular/core';

import { Plugins } from '@capacitor/core';
import { AdOptions, AdSize, AdPosition } from 'capacitor-admob';
import { async } from '@angular/core/testing';

const { AdMob, Toast } = Plugins;

@Component({
  selector: 'app-home',
  templateUrl: 'home.page.html',
  styleUrls: ['home.page.scss'],
})
export class HomePage {

  

  constructor() {}

  // Show basic Smart Banner.

  showBanner() {
    const options: AdOptions = {
      adId: 'ca-app-pub-3940256099942544/6300978111',
      adSize: AdSize.SMART_BANNER,
      position: AdPosition.BOTTOM_CENTER,
      hasTabBar: false,  // make it true if you have TabBar Layout.
      tabBarHeight: 56  // you can assign custom margin in pixel default is 56
    };

    // Show Banner Ad
    AdMob.showBanner(options)
        .then(
            async (value) => {
              console.log(value);  // true
              await Toast.show({
                text: 'Showing Banner AD.'
              })
            },
            (error) => {
              console.error(error); // show error
            }
        );


    // Subscibe Banner Event Listener
    AdMob.addListener('onAdLoaded', async (info: boolean) => {
      console.log('Showing Banner AD.');
    });
  }


  // This Banner AD have bottom margin to avoid TabBar Overlaping for TabBar 
  showTabBarBanner(){
    const options: AdOptions = {
      adId: 'ca-app-pub-3940256099942544/6300978111',
      adSize: AdSize.SMART_BANNER,
      position: AdPosition.BOTTOM_CENTER,
      hasTabBar: true,  // make it true if you have TabBar Layout.
      tabBarHeight: 56  // you can assign custom margin in pixel default is 56
    };

    // Show Banner Ad
    AdMob.showBanner(options)
        .then(
            async (value) => {
              console.log(value);  // true
              await Toast.show({
                text: 'Showing Banner AD.'
              })
            },
            (error) => {
              console.error(error); // show error
            }
        );


    // Subscibe Banner Event Listener
    AdMob.addListener('onAdLoaded', async (info: boolean) => {
      console.log('Showing TabBar Banner AD.');
    });
  }


  hideBanner() {
    
    AdMob.hideBanner().then(
      async (value) => {
          await Toast.show({
            text: 'Banner AD Hidden'
          })
          console.log(value);  // true
      },
      (error) => {
          console.error(error); // show error
      } 
    );
  }


  
  resumeBanner() {
    // Resume the banner, show it after hide

    AdMob.resumeBanner().then(
        (value) => {
            console.log(value);  // true
        },
        (error) => {
            console.error(error); // show error
        } 
    );
  }


  removeBanner() {
    // Destroy the banner, remove it from screen.

    AdMob.removeBanner().then(
      (value) => {
          console.log(value);  // true
      },
      (error) => {
          console.error(error); // show error
      } 
    );
  }


  loadInterstitial(){
    const options: AdOptions = {
      adId: 'ca-app-pub-3940256099942544/1033173712',
      autoShow: false
    }
    AdMob.prepareInterstitial(options)
        .then(
            async (value) => {
              if(value) {
                await Toast.show({
                  text: 'Interstitial AD Loaded'
                });
              }
                console.log(value);  // true
            },
            (error) => {
                console.error(error); // show error
            } 
        );
  }


  showInterstitial() {
    AdMob.showInterstitial().then(
      (value) => {
          console.log(value);  // true
      },
      (error) => {
          console.error(error); // show error
      } 
    );
  }



  loadInterstitialVideo() {
    const options: AdOptions = {
      adId: 'ca-app-pub-3940256099942544/8691691433',
      autoShow: false
    }
    AdMob.prepareInterstitial(options)
        .then(
            async (value) => {
              
              if(value) {
                await Toast.show({
                  text: 'Interstitial AD Loaded'
                });
              }
              
              console.log(value);  // true
            },
            (error) => {
                console.error(error); // show error
            } 
        );
  }


  showInterstitialVideo() {
    AdMob.showInterstitial().then(
      (value) => {
          console.log(value);  // true
      },
      (error) => {
          console.error(error); // show error
      } 
    );
  }



  prepareRewardVideo() {
    const options: AdOptions = {
      adId: 'ca-app-pub-3940256099942544/5224354917'
    }
    // Prepare ReWardVideo
    AdMob.prepareRewardVideoAd(options)
    .then(
        async (value) => {
          if(value) {
            await Toast.show({
              text: 'Reward Video AD Loaded'
            });
          }
          console.log(value);  // true
        },
        (error) => {
            console.error(error); // show error
        } 
    );
  }



  showRewardVideo() {
    // Show a RewardVideo AD

    AdMob.showRewardVideoAd().then(
      (value) => {
          console.log(value);  // true
      },
      (error) => {
          console.error(error); // show error
      } 
    );
  }

}
