package app.xplatform.capacitor.plugins;

import android.Manifest;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.getcapacitor.JSObject;
import com.getcapacitor.NativePlugin;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

import java.util.ArrayList;
import java.util.List;


@NativePlugin(
    permissions = {
        Manifest.permission.ACCESS_NETWORK_STATE,
        Manifest.permission.INTERNET
    }
)
public class AdMob extends Plugin {

    private PluginCall call;

    private ViewGroup mViewGroup;


    private RelativeLayout mAdViewLayout;


    private AdView mAdView;


    private InterstitialAd mInterstitialAd;


    private RewardedVideoAd mRewardedVideoAd;




    // Initialize AdMob with appId
    @PluginMethod()
    public void initialize(PluginCall call) {
        /* Sample AdMob App ID: ca-app-pub-3940256099942544~3347511713 */
        String appId = call.getString("appId", "ca-app-pub-3940256099942544~3347511713");

        try {
            MobileAds.initialize(this.getContext(), appId);

            List<String> testIds = call.getArray("testIds").toList();

            if (testIds.size() > 0) {
                List<String> testDevices = new ArrayList<>();
                testDevices.add(AdRequest.DEVICE_ID_EMULATOR);

                RequestConfiguration requestConfiguration
                        = new RequestConfiguration.Builder()
                        .setTestDeviceIds(testDevices)
                        .build();
                MobileAds.setRequestConfiguration(requestConfiguration);
            }


            mViewGroup = (ViewGroup) ((ViewGroup) getActivity().findViewById(android.R.id.content)).getChildAt(0);

            call.success();

        }catch (Exception ex) {
            call.error(ex.getLocalizedMessage(), ex);
        }
    }


    // Show a banner Ad
    @PluginMethod()
    public void showBanner(PluginCall call) {
        /* Dedicated test ad unit ID for Android banners: ca-app-pub-3940256099942544/6300978111*/
        String adId       = call.getString("adId", "ca-app-pub-3940256099942544/6300978111");
        String adSize     = call.getString("adSize", "SMART_BANNER");
        String adPosition = call.getString("position", "BOTTOM_CENTER");

        try {

            if (mAdView == null) {
                mAdView = new AdView(getContext());
                mAdView.setAdUnitId(adId);



                Log.d(getLogTag(), "Ad ID: "+ adId);


                switch (adSize) {
                    /*case "SMART_BANNER":
                        mAdView.setAdSize(AdSize.SMART_BANNER);
                        break;*/
                    case "BANNER":
                        mAdView.setAdSize(AdSize.BANNER);
                        break;
                    case "FLUID":
                        mAdView.setAdSize(AdSize.FLUID);
                        break;
                    case "FULL_BANNER":
                        mAdView.setAdSize(AdSize.FULL_BANNER);
                        break;
                    case "LARGE_BANNER":
                        mAdView.setAdSize(AdSize.LARGE_BANNER);
                        break;
                    case "LEADERBOARD":
                        mAdView.setAdSize(AdSize.LEADERBOARD);
                        break;
                    case "MEDIUM_RECTANGLE":
                        mAdView.setAdSize(AdSize.MEDIUM_RECTANGLE);
                        break;
                    default:
                        mAdView.setAdSize(AdSize.SMART_BANNER);
                        break;
                }
            }



            // Setup AdView Layout

            mAdViewLayout = new RelativeLayout(getContext());
            mAdViewLayout.setHorizontalGravity(Gravity.CENTER_HORIZONTAL);
            mAdViewLayout.setVerticalGravity(Gravity.BOTTOM);

            final CoordinatorLayout.LayoutParams mAdViewLayoutParams = new CoordinatorLayout.LayoutParams(
                    CoordinatorLayout.LayoutParams.WRAP_CONTENT,
                    CoordinatorLayout.LayoutParams.WRAP_CONTENT
            );



            switch (adPosition) {
                case "TOP_CENTER":
                    mAdViewLayoutParams.gravity = Gravity.TOP;
                    break;
                case "CENTER":
                  mAdViewLayoutParams.gravity = Gravity.CENTER;
                  break;
                case "BOTTOM_CENTER":
                  mAdViewLayoutParams.gravity = Gravity.BOTTOM | Gravity.CENTER;
                  break;
                default:
                  mAdViewLayoutParams.gravity = Gravity.BOTTOM;
                  break;
            }


            // Set Bottom margin for TabBar
            boolean hasTabBar = call.getBoolean("hasTabBar", false);
            if (hasTabBar) {
                float density = getContext().getResources().getDisplayMetrics().density;
                float tabBarHeight = call.getInt("tabBarHeight", 56);
                int margin = (int) (tabBarHeight * density);
                mAdViewLayoutParams.setMargins(0, 0, 0, margin);
            }


            // Remove child from AdViewLayout
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    if (mAdView.getParent() != null) {
                        ((ViewGroup)mAdView.getParent()).removeView(mAdView);
                    }
                    mAdViewLayout.setLayoutParams(mAdViewLayoutParams);
                    // Add AdView into AdViewLayout
                    mAdViewLayout.addView(mAdView);
                }
            });


            // Run AdMob In Main UI Thread
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mAdView.loadAd(new AdRequest.Builder().build());

                    mAdView.setAdListener(new AdListener(){
                        @Override
                        public void onAdLoaded() {
                            notifyListeners("onAdLoaded", new JSObject().put("value", true));
                            super.onAdLoaded();
                        }

                        @Override
                        public void onAdFailedToLoad(int i) {
                            notifyListeners("onAdFailedToLoad", new JSObject().put("errorCode", i));
                            super.onAdFailedToLoad(i);
                        }

                        @Override
                        public void onAdOpened() {
                            notifyListeners("onAdOpened", new JSObject().put("value", true));
                            super.onAdOpened();
                        }

                        @Override
                        public void onAdClosed() {
                            notifyListeners("onAdClosed", new JSObject().put("value", true));
                            super.onAdClosed();
                        }
                    });

                    // Add AdViewLayout top of the WebView
                    mViewGroup.addView(mAdViewLayout);
                }
            });

            call.success(new JSObject().put("value", true));

        }catch (Exception ex) {
            call.error(ex.getLocalizedMessage(), ex);
        }
    }


    // Hide the banner, remove it from screen, but can show it later
    @PluginMethod()
    public void hideBanner(PluginCall call) {
        try {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mAdViewLayout != null) {
                        mAdViewLayout.setVisibility(View.GONE);
                        mAdView.pause();
                    }
                }
            });

            call.success(new JSObject().put("value", true));

        }catch (Exception ex) {
            call.error(ex.getLocalizedMessage(), ex);
        }
    }


    // Resume the banner, show it after hide
    @PluginMethod()
    public void resumeBanner(PluginCall call) {
        try {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mAdViewLayout != null && mAdView != null) {
                        mAdViewLayout.setVisibility(View.VISIBLE);
                        mAdView.resume();
                        Log.d(getLogTag(), "Banner AD Resumed");
                    }
                }
            });

            call.success(new JSObject().put("value", true));

        }catch (Exception ex) {
            call.error(ex.getLocalizedMessage(), ex);
        }
    }


    // Destroy the banner, remove it from screen.
    @PluginMethod()
    public void removeBanner(PluginCall call) {
        try {
            if (mAdView != null) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mAdView != null) {
                            mViewGroup.removeView(mAdViewLayout);
                            mAdViewLayout.removeView(mAdView);
                            mAdView.destroy();
                            mAdView = null;
                            Log.d(getLogTag(), "Banner AD Removed");
                        }
                    }
                });
            }

            call.success(new JSObject().put("value", true));

        }catch (Exception ex) {
            call.error(ex.getLocalizedMessage(), ex);
        }
    }




    // Prepare interstitial Ad
    @PluginMethod()
    public void prepareInterstitial(final PluginCall call) {
        this.call = call;
        /* dedicated test ad unit ID for Android interstitials:
            ca-app-pub-3940256099942544/1033173712
        */
        String adId = call.getString("adId", "ca-app-pub-3940256099942544/1033173712");


        try {

            mInterstitialAd = new InterstitialAd(getContext());
            mInterstitialAd.setAdUnitId(adId);


            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mInterstitialAd.loadAd(new AdRequest.Builder().build());

                    mInterstitialAd.setAdListener(new AdListener() {
                        @Override
                        public void onAdLoaded() {
                            // Code to be executed when an ad finishes loading.
                            notifyListeners("onAdLoaded", new JSObject().put("value", true));
                            call.success(new JSObject().put("value", true));
                            super.onAdLoaded();

                        }

                        @Override
                        public void onAdFailedToLoad(int errorCode) {
                            // Code to be executed when an ad request fails.
                            notifyListeners("onAdFailedToLoad", new JSObject().put("errorCode", errorCode));
                            super.onAdFailedToLoad(errorCode);
                        }

                        @Override
                        public void onAdOpened() {
                            // Code to be executed when the ad is displayed.
                            notifyListeners("onAdOpened", new JSObject().put("value", true));
                            super.onAdOpened();
                        }

                        @Override
                        public void onAdLeftApplication() {
                            // Code to be executed when the user has left the app.
                            notifyListeners("onAdLeftApplication", new JSObject().put("value", true));
                            super.onAdLeftApplication();
                        }

                        @Override
                        public void onAdClosed() {
                            // Code to be executed when when the interstitial ad is closed.
                            notifyListeners("onAdClosed", new JSObject().put("value", true));
                            super.onAdClosed();
                        }
                    });

                }
            });

        }catch (Exception ex) {
            call.error(ex.getLocalizedMessage(), ex);
        }
    }


    // Show interstitial Ad
    @PluginMethod()
    public void showInterstitial(final PluginCall call) {
        try {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mInterstitialAd.show();
                        }
                    });
                    call.success(new JSObject().put("value", true));
                } else {
                    call.error("The interstitial wasn't loaded yet.");
                }
                }
            });
        }catch (Exception ex){
            call.error(ex.getLocalizedMessage(), ex);
        }
    }




    // Prepare a RewardVideoAd
    @PluginMethod()
    public void prepareRewardVideoAd(final PluginCall call) {
        this.call = call;
        /* dedicated test ad unit ID for Android rewarded video:
            ca-app-pub-3940256099942544/5224354917
        */
        final String adId = call.getString("adId", "ca-app-pub-3940256099942544/5224354917");

        try {
            mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(getContext());

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mRewardedVideoAd.loadAd(adId, new AdRequest.Builder().build());


                    mRewardedVideoAd.setRewardedVideoAdListener(new RewardedVideoAdListener() {
                        @Override
                        public void onRewardedVideoAdLoaded() {
                            call.success(new JSObject().put("value", true));
                            notifyListeners("onRewardedVideoAdLoaded", new JSObject().put("value", true));
                        }

                        @Override
                        public void onRewardedVideoAdOpened() {
                            notifyListeners("onRewardedVideoAdOpened", new JSObject().put("value", true));
                        }

                        @Override
                        public void onRewardedVideoStarted() {
                            notifyListeners("onRewardedVideoStarted", new JSObject().put("value", true));
                        }

                        @Override
                        public void onRewardedVideoAdClosed() {
                            notifyListeners("onRewardedVideoAdClosed", new JSObject().put("value", true));
                        }

                        @Override
                        public void onRewarded(RewardItem rewardItem) {
                            notifyListeners("onRewarded", new JSObject().put("value", true));
                        }

                        @Override
                        public void onRewardedVideoAdLeftApplication() {
                            notifyListeners("onRewardedVideoAdLeftApplication", new JSObject().put("value", true));
                        }

                        @Override
                        public void onRewardedVideoAdFailedToLoad(int i) {
                            notifyListeners("onRewardedVideoAdFailedToLoad", new JSObject().put("value", true));
                        }

                        @Override
                        public void onRewardedVideoCompleted() {
                            notifyListeners("onRewardedVideoCompleted", new JSObject().put("value", true));
                        }
                    });
                }
            });


        }catch (Exception ex) {
            call.error(ex.getLocalizedMessage(), ex);
        }

    }

   // Show a RewardVideoAd
   @PluginMethod()
   public void showRewardVideoAd(final PluginCall call) {
       try {
           getActivity().runOnUiThread(new Runnable() {
               @Override
               public void run() {
                   if (mRewardedVideoAd != null && mRewardedVideoAd.isLoaded()) {
                       getActivity().runOnUiThread(new Runnable() {
                           @Override
                           public void run() {
                               mRewardedVideoAd.show();
                           }
                       });
                       call.success(new JSObject().put("value", true));
                   }else {
                       call.error("The RewardedVideoAd wasn't loaded yet.");
                   }
               }
           });

       }catch (Exception ex) {
           call.error(ex.getLocalizedMessage(), ex);
       }
   }


    // Pause a RewardVideoAd
    @PluginMethod()
    public void pauseRewardedVideo(PluginCall call) {
        try {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mRewardedVideoAd.pause(getContext());
                }
            });
            call.success(new JSObject().put("value", true));
        }catch (Exception ex) {
            call.error(ex.getLocalizedMessage(), ex);
        }
    }


    // Resume a RewardVideoAd
    @PluginMethod()
    public void resumeRewardedVideo(PluginCall call) {
        try {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mRewardedVideoAd.resume(getContext());
                }
            });
            call.success(new JSObject().put("value", true));
        }catch (Exception ex) {
            call.error(ex.getLocalizedMessage(), ex);
        }
    }


    // Destroy a RewardVideoAd
    @PluginMethod()
    public void stopRewardedVideo(PluginCall call) {
        try {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mRewardedVideoAd.destroy(getContext());
                }
            });
            call.success(new JSObject().put("value", true));
        }catch (Exception ex) {
            call.error(ex.getLocalizedMessage(), ex);
        }
    }
}
