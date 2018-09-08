package com.nyrds.pixeldungeon.support;

import android.content.Intent;
import android.net.Uri;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.nyrds.pixeldungeon.ml.EventCollector;
import com.watabou.noosa.Game;

class AAdsBannerProvider implements AdsUtilsCommon.IBannerProvider {

    @Override
    public void displayBanner() {

        if(Game.instance().checkOwnSignature()) {
            AdsUtilsCommon.bannerFailed(AAdsBannerProvider.this);
            return;
        }

        WebView adView = new WebView(Game.instance());

        LinearLayout layout = Game.instance().getLayout();
        int adViewHeight = Math.max(50, layout.getHeight() / 10);

        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, adViewHeight);
        adView.setLayoutParams(params);


        adView.setWebViewClient(new WebViewClient() {

            public void onPageFinished(WebView view, String url) {
            }

            @Override
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
                AdsUtilsCommon.bannerFailed(AAdsBannerProvider.this);
                EventCollector.logEvent("banner", "aads_no_banner", description);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                Game.instance().startActivity(browserIntent);
                return true;
            }
        });

        adView.loadUrl("https://acceptable.a-ads.com/995145");

        Game.instance().getLayout().addView(adView, 0);
        Game.setNeedSceneRestart(true);
    }

}