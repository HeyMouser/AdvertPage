package com.taikang.tkda.advertpage;

import java.util.ArrayList;
import java.util.List;

public class ResourceHelp {
    private String image = "https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1582273722&di=b5ecf3fcc90f2213ca38393a3543de8d&src=http://file02.16sucai.com/d/file/2014/0822/b44cd1310d09026f6dd1f0633a1cc2a5.jpg";
    private String image1 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1582604418535&di=e526cfb351918719455e4e7b93b036d4&imgtype=0&src=http%3A%2F%2Fa3.att.hudong.com%2F68%2F61%2F300000839764127060614318218_950.jpg";
    private String image2 = "http://a0.att.hudong.com/16/12/01300535031999137270128786964.jpg";
    private String image3 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1582604713053&di=06a040af34f3716862fa915004d40687&imgtype=0&src=http%3A%2F%2Fwww.pc6.com%2Fuploadimages%2F2009-11%2F2009110656168237.jpg";
    private String image4 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1582605006636&di=e451fc551c8114c9ff0f2af48e75d0c3&imgtype=0&src=http%3A%2F%2Fpic.87g.com%2Fupload%2F2016%2F0903%2F20160903044921740.jpg";

    private String gif = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1582536213173&di=a7d2d9c27bfaadd1058405c2a91abcb1&imgtype=0&src=http%3A%2F%2F5b0988e595225.cdn.sohucs.com%2Fimages%2F20170917%2F9ab8f252a2ca4587a3391a6140bb3194.gif";
    private String gif1 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1582550306478&di=120cf6e69d19a973ac4d5540d32eed69&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201410%2F23%2F20141023144603_8Mfxm.gif";
    private String gif2 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1582605066190&di=f11c63078f1483b3a838b6accd534992&imgtype=0&src=http%3A%2F%2Fimg.eeyy.com%2Fuploadfile%2F2017%2F0713%2F20170713090440689.gif";
    private String gif3 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1582605234646&di=bf522a7f0c3baea427fabdbcd74e49ca&imgtype=0&src=http%3A%2F%2Fp2.qhimgs4.com%2Ft014d8266ba396550c4.gif";
    private String gif4 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1582605453675&di=4bdb746bcc142f7d89d8b3007fbcc1fd&imgtype=0&src=http%3A%2F%2Fspider.nosdn.127.net%2F48f12e570b13e6441f8ad8ec75a44ddb.gif";

    private List<String> gifs;
    private List<String> images;

    private int radom = 0;

    public ResourceHelp() {
        gifs = new ArrayList<>();
        images = new ArrayList<>();

        gifs.add(gif);
        gifs.add(gif1);
        gifs.add(gif2);
        gifs.add(gif3);
        gifs.add(gif4);

        images.add(image);
        images.add(image1);
        images.add(image2);
        images.add(image3);
        images.add(image4);
        radom = (int) ((Math.random() * 5));
    }

    public String getGif() {
        return gifs.get(radom);
    }

    public String getImage() {
        return images.get(radom);
    }
}
