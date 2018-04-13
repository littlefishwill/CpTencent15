package com.lfish.lotteryssc.net;

import android.content.Context;
import android.util.Log;

import com.lfish.lotteryssc.dao.News;
import com.lfish.lotteryssc.net.dao.Result;
import com.lfish.lotteryssc.net.dao.ResultSwitch;
import com.lfish.lotteryssc.net.dao.YYResult;
import com.lfish.lotteryssc.net.novate.HeaderInterceptor;
import com.lfish.lotteryssc.net.novate.RetrofitClient;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;

/**
 * 接口请求
 */

public class RetrofitManager {
        private static RetrofitClient client;
        private static ApiService apiService;
        private static final String baseUrl = "http://route.showapi.com/44-1/";
        private static final int TIMEOUT = 60;//请求超时时间


        public String getManagerName(Context context) {
            return null;
        }

        /**
         * 初始化网络请求
         *
         */
        public void init(Context context) {
            client = new RetrofitClient.Builder(context)
                    .connectTimeout(TIMEOUT, TimeUnit.SECONDS).writeTimeout(TIMEOUT,TimeUnit.SECONDS).readTimeout(TIMEOUT,TimeUnit.SECONDS)
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(baseUrl)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
//                .addSSLSocketFactory(NovateHttpsFactroy.getSSLSocketFactory())
                    .addInterceptor(new HeaderInterceptor())
                    .build();

            apiService = client.create(ApiService.class);

    }

    private static class SingletonHolder {
        static final RetrofitManager INSTANCE = new RetrofitManager();
    }
//  JVM本身机制保证了线程安全问题,读取实例的时候不会进行同步，没有性能缺陷；也不依赖JDK版本
    public static RetrofitManager getInstance() {
        return SingletonHolder.INSTANCE;
    }


//    /**
//     * 影片接口
//     *
//     */
//    public void sendFilmTicket(final String url, CommentSubscriber<Result<ArrayList<FilmInfo>>> subscriber) {
//        client.call(apiService.sendFilmTicket(url), subscriber);
//        Log.e("http","影片："+url);
//    }
//
//    /**
//     * 售票场次
//     *
//     */
//    public void sendScreeningsTicket(final String url, CommentSubscriber<Result<ArrayList<ScreeningInfo>>> subscriber) {
//        client.call(apiService.sendScreeningsTicket(url), subscriber);
//        Log.e("http","场次："+url);
//    }
//
//    /**
//     * 影片和场次接口
//     *
//     */
//    public void sendFilmAndHallTicket(final String url, CommentSubscriber<Result<FilmShowsRT>> subscriber) {
//        client.call(apiService.sendFilmAndHallTicket(url), subscriber);
//        Log.e("http","影片和场次："+url);
//    }
//
//    /**
//     * 售票座位
//     */
//    public void sendSeatTicket(final String url, CommentSubscriber<Result<ArrayList<SeatInfo>>> subscriber) {
//        client.call(apiService.sendSeatTicket(url), subscriber);
//        Log.e("http","座位："+url);
//    }
//
//    /**
//     * 获取卖品列表
//     *
//     *
//     */
//    public void sendGoodList(final String url, CommentSubscriber<Result<ArrayList<GoodsInfo>>> subscriber) {
//        client.call(apiService.sendGoodList(url), subscriber);
//        Log.e("http","卖品："+url);
//    }
//
//    /**
//     * 创建总订单
//     *
//     */
//    public void sendOrderMain(final String url,HashMap<String,String> map, CommentSubscriber<Result<MainOrderInfo>> subscriber) {
//        client.call(apiService.sendOrderMain(url,map), subscriber);
//        Log.e("http","创建主订单："+url+":"+map.toString());
//    }
//
//    /**
//     * 创建售票子订单
//     *
//     */
//    public void sendOrderTicket(final String url,HashMap<String,String> map, CommentSubscriber<Result> subscriber) {
//        client.call(apiService.sendOrderTicket(url,map), subscriber);
//        Log.e("http","售票子订单："+url+":"+map.toString());
//    }
//
//    /**
//     * 创建賣品子订单
//     *
//     */
//    public void sendOrderGood(final String url,final HashMap<String,String> map, CommentSubscriber<Result> subscriber) {
//        client.call(apiService.sendOrderGood(url,map), subscriber);
//        Log.e("http","卖品子订单："+url+":"+map.toString());
//    }
//
//    /**
//     * 创建支付子订单
//     *
//     */
//    public void sendOrderPay(final String url,final HashMap<String,String> map, CommentSubscriber<Result<ResultPay>> subscriber) {
//        client.call(apiService.sendOrderPay(url,map), subscriber);
//        Log.e("http","支付子订单："+url+":"+map.toString());
//    }
//
//    /**
//     * 取消订单
//     *
//     */
//    public void sendOrderCancel(final String url,final HashMap<String,String> map, CommentSubscriber<Result> subscriber) {
//        client.call(apiService.sendOrderCancel(url,map), subscriber);
//        Log.e("http","取消订单："+url+":"+map.toString());
//    }
//
//    /**
//     *
//     * 鼎新取票
//     */
//    public void sendGetTicketDX(final String url, CommentSubscriber<DXBaseResult<DXResult<DXTicketInfo>>> subscriber){
//        client.call(apiService.sendGetTicketDX(url),subscriber);
//    }
//
//    /**
//     *
//     * 获取优惠券
//     */
//    public void sendGetCoupon(final String url, CommentSubscriber<Result<ArrayList<CouponInfo>>> subscriber){
//        client.call(apiService.sendGetCoupon(url),subscriber);
//        Log.e("http","优惠券："+url);
//    }
//
//    /**
//     *
//     * 请求设备信息
//     */
//    public void sendEquipmentInformation(final String url,CommentSubscriber<Result<CinemaInfo>> subscriber){
//        client.call(apiService.sendEquipmentInformation(url),subscriber);
//        Log.e("http","设备信息:"+url);
//    }
//
//    /**
//     *
//     * 请求开关
//     */
//    public void sendIniSwitch(final String url, CommentSubscriber<Result<Switch>> subscriber){
//        client.call(apiService.sendInitSwitch(url),subscriber);
//        Log.e("http","开关:"+url);
//    }
//
//    /**
//     *
//     * 请求字体接口
//     */
//    public void sendInitFont(final String url, CommentSubscriber<Result<ArrayList<Font>>> subscriber){
//        client.call(apiService.sendInitFont(url),subscriber);
//        Log.e("http","字体："+url);
//    }
//
//    /**
//     *
//     * 请求皮肤接口
//     */
//    public void sendInitSkin(final String url, CommentSubscriber<Result<ArrayList<Skin>>> subscriber){
//        client.call(apiService.sendInitSkin(url),subscriber);
//        Log.e("http","皮肤："+url);
//    }
//
//    /**
//     *
//     * 请求广告信息
//     */
//    public void sendAdRequest(final String url,CommentSubscriber<Result<ArrayList<AdInfo>>> subscriber){
//        Log.e("http","广告："+url);
//        client.call(apiService.sendAdRequest(url),subscriber);
//    }
//    /**
//     *
//     * 请求升级信息
//     */
//    public void sendCheckUpdate(final String url, CommentSubscriber<Result<UpgradeMsg>> subscriber){
//        client.call(apiService.sendUpdate(url),subscriber);
//        Log.e("http","升级："+url);
//    }
//
//    /**
//     * 获取游戏信息
//     *
//     */
//    public void sendGameList(final String url, Subscriber<Result<Games>> subscriber) {
//        client.call(apiService.sendGameList(url), subscriber);
//        Log.e("http","游戏："+url);
//    }
//
//    /**
//     * 获取游戏奖品详情
//     *
//     */
//    public void sendGameAwardInfo(final String url, Subscriber<Result<ArrayList<Award>>> subscriber) {
//        client.call(apiService.sendGameAwardInfo(url), subscriber);
//        Log.e("http","奖品信息:"+url);
//    }
//
//    /**
//     * 获取游戏奖品
//     *
//     */
//    public void sendGameAward(final String url,Subscriber<Result<Award>> subscriber) {
//        client.call(apiService.sendGameAward(url), subscriber);
//        Log.e("http","中奖奖品:"+url);
//    }
//
//
//    /**
//     * 玩游戏验证取票码是否有效
//     *
//     */
//    public void sendGameCheckTicketCode(final String url,Subscriber<Result> subscriber) {
//        client.call(apiService.sendGameCheckTicketCode(url), subscriber);
//        Log.e("http","玩游戏验证取票码是否有效:"+url);
//    }
//
//    /**
//     * 鼎新核销
//     *
//     */
//    public void sendDxDestroy(final String url,Subscriber<DXBaseResult<DXResult<Object>>> subscriber) {
//        client.call(apiService.sendDxDestroy(url), subscriber);
//        Log.e("http","鼎新核销接口:"+url);
//    }
//
//    /**
//     * 获取会员卡打折金额
//     */
//    public void sendVipDiscountMoney(String url,HashMap<String,String> map, Subscriber<Result<VipDiscountResultInfo>> subscriber) {
//        client.call(apiService.sendVipDiscountMoney(url,map), subscriber);
//        Log.e("http","获取会员卡打折金额:"+url+"---map:"+map.toString());
//    }
//
//    /**
//     * 火凤凰 http 取票
//     */
//    public void sendHfhGetTicket(String url,Subscriber<HfhBaseTicketInfo<HfhTicketInfo>> subscriber){
//        client.call(apiService.sendHfhGetTicket(url),subscriber);
//        Log.e("http","火凤凰 http 取票:"+url);
//    }
//
//    /**
//     * 火凤凰 http 打印前核销
//     */
//    public void sendHfhAgoDestroy(String url,Subscriber<HfhBaseTicketInfo> subscriber){
//        client.call(apiService.sendHfhAgoDestroy(url),subscriber);
//        Log.e("http","火凤凰 http 打印前核销:"+url);
//    }
//
//    /**
//     * 初始化 时间
//     */
//    public void sendTimeInitManager(String url,CommentSubscriber<Result<Long>> subscriber){
//        client.call(apiService.sendInitTime(url),subscriber);
//        Log.e("http","服务器同步时间:"+url);
//    }
//
//
//    /**
//     * 会员专区 会员卡登陆
//     */
//    public void sendVipAreaLogin(String url,HashMap<String,String> map,CommentSubscriber<Result<VipDetailInfo>> subscriber){
//        client.call(apiService.sendVipAreaLogin(url,map), subscriber);
//        Log.e("http","会员专区  登陆:"+url+":"+map.toString());
//    }
//
//
//    /**
//     * 会员专区 获取会员卡充值金额
//     *
//     */
//    public void sendVipRecharge(final String url, HashMap<String,String> map, Subscriber<Result<VipRechargeInfo>> subscriber) {
//        client.call(apiService.sendVipRecharge(url,map), subscriber);
//        Log.e("http","会员专区  获取充值金额:"+url+":"+map.toString());
//    }
//
//    /**
//     * 会员专区 会员卡充值支付
//     *
//     */
//    public void sendVipRechargePay(String url, HashMap<String,String> map, Subscriber<Result<String>> subscriber) {
//        client.call(apiService.sendVipRechargePay(url,map), subscriber);
//        Log.e("http","会员专区 充值支付:"+url+":"+map.toString());
//    }
//
//    /**
//     * 会员专区 卖品列表
//     *
//     */
//    public void sendVipAreaGoodsList(String url,String token, Subscriber<Result<ArrayList<GoodsInfo>>> subscriber) {
//        client.call(apiService.sendVipAreaGoodList(url,token), subscriber);
//        Log.e("http","会员专区 卖品列表:"+url+":token:"+token);
//    }
//
//    /**
//     * 会员专区 影片和场次
//     */
//    public void sendVipAreaFilmAndHallTicket(final String url,String token, CommentSubscriber<Result<FilmShowsRT>> subscriber) {
//        client.call(apiService.sendVipAreaFilmAndHallList(url,token), subscriber);
//        Log.e("http","会员专区 影片和场次："+url+"--token---"+token);
//    }
//
//    /**
//     * 会员专区 会员卡退出
//     */
//    public void sendVipAreaLogOut(String url,String token,CommentSubscriber<Result> subscriber){
//        client.call(apiService.sendVipAreaLogOut(url,token), subscriber);
//        Log.e("http","会员专区  退出:"+url+":token-"+token);
//    }
//
//
//    /**
//     * 上传广告监控数据
//     */
//    public void sendAdData(String url, File file, Subscriber<Result> subscriber){
//        RequestBody requestBody = new MultipartBody.Builder()
//                .setType(MultipartBody.FORM)
//                .addFormDataPart("md5Code", MD5Util.getFileMD5(file))
//                .addFormDataPart("file", file.getName(), RequestBody.create(MediaType.parse("text/plain"), file))
//                .build();
//        client.call(apiService.sendAdData(url,requestBody),subscriber);
//        Log.e("广告监控上报:"+url);
//    }
//
//    /**
//     * 取票上报请求
//     */
//     volatile int SendSum=0;
//    public void sendTicket(final TerminalReport terminalReport) {
//        terminalReport.setReportId(Constant.DEVICE_SN + System.currentTimeMillis());
//        terminalReport.setTerminalSn(Constant.DEVICE_SN);
//        terminalReport.setReportTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
//        terminalReport.setCinemaCode(Constant.CINEMA_CODE);
//        List<TerminalReport> list=new ArrayList<>();
//        list.add(terminalReport);
//        String URL=Constant.SERVER_BACKENDIP+ "/backend/rest/equ/report";
//        Log.e("http","取票上报:"+URL+":"+new Gson().toJson(list));
//        RequestBody requestBody = new MultipartBody.Builder()
//                .addFormDataPart("reportJson",new Gson().toJson(list)).build();
//        client.call(apiService.sendTicket(URL,requestBody), new Subscriber<Result>() {
//            @Override
//            public void onStart() {
//            }
//            @Override
//            public void onError(Throwable e) {
//                e.printStackTrace();
//                Log.e("http","上报取票数失败"+e.getMessage());
//                SendSum++;
//                DbManger.save(terminalReport);
//                if(SendSum<3){
//                    List<TerminalReport> terminalReports= DbManger.getQueryAll(TerminalReport.class);
//                    if(terminalReports!=null && terminalReports.size()>0){
//                        Log.e("http","上报取票数失败之后--"+terminalReports.size()+"--SendSum--"+SendSum);
//                        for (TerminalReport terminalReport1:terminalReports){
//                            DbManger.delete(terminalReport1);
//                            sendTicket(terminalReport1);
//                        }
//
//                    }
//                }
//            }
//
//            @Override
//            public void onNext(Result result) {
//                if (null != result && result.getCode()== 1 ) {
//                    Log.e("http","上报取票数成功");
//                    SendSum=0;
//                    List<TerminalReport> terminalReports= DbManger.getQueryAll(TerminalReport.class);
//                    if(terminalReports!=null && terminalReports.size()>0){
//                        if(terminalReports.size()>10){
//                            for (int i=0;i<10;i++){
//                                DbManger.delete(terminalReports.get(i));
//                                sendTicket(terminalReports.get(i));
//                            }
//                        }else{
//                            for (TerminalReport terminalReport1:terminalReports){
//                                DbManger.delete(terminalReport1);
//                                sendTicket(terminalReport1);
//                            }
//                        }
//                    }
//                }
//            }
//            @Override
//            public void onCompleted() {
//            }
//        });
//    }
//
//    /**
//     * 发送影票仔细信息
//     */
//    public void sendTicketMessage( String url, HashMap<String,String> map, Subscriber<Result> subscriber) {
//        client.call(apiService.sendTicketMessage(url,map), subscriber);
//        Log.e("http","发送影票信息"+url+":"+map.toString());
//    }
//
//    /**
//     * 上传 log 日志文件
//     */
//    public void sendLogData(String url, File file, Subscriber<Result> subscriber){
//        Log.e("Log上报:"+url);
//        RequestBody requestBody = new MultipartBody.Builder()
//                .setType(MultipartBody.FORM)
//                .addFormDataPart("terminalSn",Constant.DEVICE_SN)
//                .addFormDataPart("file", file.getName(), RequestBody.create(MediaType.parse("text/plain"), file))
//                .build();
//        client.call(apiService.sendLogUpload(url,requestBody),subscriber);
//    }
//
//    /**
//     * 发送开关状态
//     *
//     */
//    public void sendSwitchStatue(String url, HashMap<String,String> map, Subscriber<Result> subscriber) {
//        client.call(apiService.sendSwitchStatue(url,map), subscriber);
//        Log.e("http","发送开关状态:"+url+":"+map.toString());
//    }
//
//    /**
//     * 获取优惠券排期
//     */
//    public void getCouponSchedule(String url, CommentSubscriber<Result<ArrayList<Schedule>>> subscriber){
//        client.call(apiService.getCouponSchedule(url),subscriber);
//        Log.e("http","优惠券排期："+url);
//    }
//
//    /**
//     * 获取优惠券排期
//     */
//    public void getCouponMaterials(String url, CommentSubscriber<Result<ArrayList<Materials>>> subscriber){
//        client.call(apiService.getCouponMaterials(url),subscriber);
//        Log.e("http","优惠券素材："+url);
//    }
//
//    /**
//     * 获取优惠券排期
//     */
//    public void getCouponVoucher(String url, CommentSubscriber<Result<Voucher>>subscriber){
//        client.call(apiService.getCouponVoucher(url),subscriber);
//        Log.e("http","优惠券素材："+url);
//    }
//
//    /**
//     * 获取优惠券排期
//     */
//    public void verificationVoucher(String url, CommentSubscriber<Result>subscriber){
//        client.call(apiService.verificationVoucher(url),subscriber);
//        Log.e("http","优惠券素材："+url);
//    }
//
//    /**
//     * 获取促销游戏奖品
//     *
//     */
//    public void sendGameResultCuXiao (String url,Subscriber<Result<GameCuXiaoResult>> subscriber) {
//        client.call(apiService.sendGameCuXiaoResult(url), subscriber);
//        Log.e("http","中奖奖品:"+url);
//    }


    /**
     * getMessage
     */
    public void getYYMSG( String url, HashMap<String,String> map, Subscriber<Result<YYResult>> subscriber) {
        client.call(apiService.sendYYmsg(url,map), subscriber);
    }

     public void getSwitch (String url,Subscriber<ResultSwitch> subscriber) {
        client.call(apiService.getSwitch(url), subscriber);
    }
    
    public void getNews (String url,Subscriber<News> subscriber) {
        client.call(apiService.getNews(url), subscriber);
    }


}
