package com.lfish.lotterysscjh.net;


import com.lfish.lotterysscjh.net.dao.Result;
import com.lfish.lotterysscjh.net.dao.ResultSwitch;
import com.lfish.lotterysscjh.net.dao.YYResult;

import java.util.HashMap;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;
import rx.Observable;

/**
 * ApiService 接口请求
 * POST 请求和GET 请求
 */

public interface ApiService {
//    /**
//     * 提交MQ 数据
//     */
//    @POST
//    Observable<Result> sendTicket(@Url String url, @Body RequestBody reportJson);
//
//    /**
//     * 创建总订单
//     */
//    @FormUrlEncoded
//    @POST
//    Observable<Result<MainOrderInfo>> sendOrderMain(@Url String url, @FieldMap HashMap<String, String> map);
//
//    /**
//     * 创建售票子订单
//     */
//    @FormUrlEncoded
//    @POST
//    Observable<Result> sendOrderTicket(@Url String url, @FieldMap Map<String, String> map);
//
//    /**
//     * 创建卖品子订单
//     */
//    @FormUrlEncoded
//    @POST
//    Observable<Result> sendOrderGood(@Url String url, @FieldMap Map<String, String> map);
//
//    /**
//     * 创建支付子订单
//     */
//    @FormUrlEncoded
//    @POST
//    Observable<Result<ResultPay>> sendOrderPay(@Url String url, @FieldMap Map<String, String> map);
//
//    /**
//     * 取消订单
//     */
//    @FormUrlEncoded
//    @POST
//    Observable<Result> sendOrderCancel(@Url String url, @FieldMap HashMap<String, String> map);
//
//    /**
//     * 获取会员卡打折金额
//     */
//    @FormUrlEncoded
//    @POST
//    Observable<Result<VipDiscountResultInfo>> sendVipDiscountMoney(@Url String url, @FieldMap HashMap<String, String> map);
//
//    /**
//     * 上报广告数据
//     */
//    @POST
//    Observable<Result> sendAdData(@Url String url, @Body RequestBody txt);
//
//    /**
//     * 会员专区 登陆
//     */
//    @FormUrlEncoded
//    @POST
//    Observable<Result<VipDetailInfo>> sendVipAreaLogin(@Url String url, @FieldMap HashMap<String, String> map);
//
//    /**
//     * 会员专区 获取会员卡充值金额
//     */
//    @FormUrlEncoded
//    @POST
//    Observable<Result<VipRechargeInfo>> sendVipRecharge(@Url String url, @FieldMap HashMap<String, String> map);
//
//    /**
//     * 会员专区 会员卡充值支付
//     */
//    @FormUrlEncoded
//    @POST
//    Observable<Result<String>> sendVipRechargePay(@Url String url, @FieldMap HashMap<String, String> map);
//
//
//    /**
//     * 会员专区 卖品列表
//     */
//    @FormUrlEncoded
//    @POST
//    Observable<Result<FilmShowsRT>> sendVipAreaFilmAndHallList(@Url String url, @Field("token") String token);
//
//    /**
//     * 会员专区 影片和场次列表
//     */
//    @FormUrlEncoded
//    @POST
//    Observable<Result<ArrayList<GoodsInfo>>> sendVipAreaGoodList(@Url String url, @Field("token") String token);
//
//    /**
//     * 会员专区 登出
//     */
//    @FormUrlEncoded
//    @POST
//    Observable<Result> sendVipAreaLogOut(@Url String url, @Field("token") String token);
//
//    /**
//     * 发送取票详细信息
//     */
//    @FormUrlEncoded
//    @POST
//    Observable<Result> sendTicketMessage(@Url String url, @FieldMap HashMap<String, String> map);
//
//
//    /**
//     * 初始化设备信息
//     */
//    @GET
//    Observable<Result<CinemaInfo>> sendEquipmentInformation(@Url String url);
//
//    /**
//     * 获取影片
//     */
//    @GET
//    Observable<Result<ArrayList<FilmInfo>>> sendFilmTicket(@Url String url);
//
//    /**
//     * 获取场次
//     */
//    @GET
//    Observable<Result<ArrayList<ScreeningInfo>>> sendScreeningsTicket(@Url String url);
//
//    /**
//     * 获取影片和场次
//     */
//    @GET
//    Observable<Result<FilmShowsRT>> sendFilmAndHallTicket(@Url String url);
//
//    /**
//     * 获取座位
//     */
//    @GET
//    Observable<Result<ArrayList<SeatInfo>>> sendSeatTicket(@Url String url);
//
//    /**
//     * 鼎新取票
//     */
//    @GET
//    Observable<DXBaseResult<DXResult<DXTicketInfo>>> sendGetTicketDX(@Url String url);
//
//    /**
//     * 鼎新核销
//     */
//    @GET
//    Observable<DXBaseResult<DXResult<Object>>> sendDxDestroy(@Url String url);
//
//    /**
//     * 广告
//     */
//    @GET
//    Observable<Result<ArrayList<AdInfo>>> sendAdRequest(@Url String url);
//
//    /**
//     * 获取优惠券
//     */
//    @GET
//    Observable<Result<ArrayList<CouponInfo>>> sendGetCoupon(@Url String url);
//
//    /**
//     * 获取卖品列表
//     */
//    @GET
//    Observable<Result<ArrayList<GoodsInfo>>> sendGoodList(@Url String url);
//
//    /**
//     * 版本更新
//     */
//    @GET
//    Observable<Result<UpgradeMsg>> sendUpdate(@Url String url);
//
//    /**
//     * 获取当前游戏列表
//     */
//    @GET
//    Observable<Result<Games>> sendGameList(@Url String url);
//
//    /**
//     * 获取奖品详情
//     */
//    @GET
//    Observable<Result<ArrayList<Award>>> sendGameAwardInfo(@Url String url);
//
//    /**
//     * 获取奖品
//     */
//    @GET
//    Observable<Result<Award>> sendGameAward(@Url String url);
//
//    /**
//     * 玩游戏前验证取票码
//     */
//    @GET
//    Observable<Result> sendGameCheckTicketCode(@Url String url);
//
//    /**
//     * 火凤凰，查询影片信息
//     */
//    @GET
//    Observable<HfhBaseTicketInfo<HfhTicketInfo>> sendHfhGetTicket(@Url String url);
//    /**
//     * 火凤凰，打印前核销
//     */
//    @GET
//    Observable<HfhBaseTicketInfo> sendHfhAgoDestroy(@Url String url);
//
//    /**
//     * 开关
//     */
//    @GET
//    Observable<Result<Switch>> sendInitSwitch(@Url String url);
//
//    /**
//     * 字体
//     */
//    @GET
//    Observable<Result<ArrayList<Font>>> sendInitFont(@Url String url);
//
//    /**
//     * 皮肤
//     */
//    @GET
//    Observable<Result<ArrayList<Skin>>> sendInitSkin(@Url String url);
//
//    /**
//     * 时间
//     */
//    @GET
//    Observable<Result<Long>> sendInitTime(@Url String url);
//
//    /**
//     * 上传log
//     */
//    @POST
//    Observable<Result> sendLogUpload(@Url String url, @Body RequestBody txt);
//
//    /**
//     * 发送状态开关
//     */
//    @FormUrlEncoded
//    @POST
//    Observable<Result> sendSwitchStatue(@Url String url, @FieldMap HashMap<String, String> map);
//
//    /**
//     * 获取优惠券排期
//     */
//    @GET
//    Observable<Result<ArrayList<Schedule>>> getCouponSchedule(@Url String url);
//
//    /**
//     * 获取优惠券排期
//     */
//    @GET
//    Observable<Result<ArrayList<Materials>>> getCouponMaterials(@Url String url);
//
//    /**
//     * 获取优惠券排期
//     */
//    @GET
//    Observable<Result<Voucher>> getCouponVoucher(@Url String url);
//
//    /**
//     * 核销促销优惠券
//     */
//    @GET
//    Observable<Result> verificationVoucher(@Url String url);
//
//    /**
//     * 获取促销游戏奖品
//     */
//    @GET
//    Observable<Result<GameCuXiaoResult>> sendGameCuXiaoResult(@Url String url);


    /**
     * 发送取票详细信息
     */
    @FormUrlEncoded
    @POST
    Observable<Result<YYResult>> sendYYmsg(@Url String url, @FieldMap HashMap<String, String> map);


    /**
     * 发送取票详细信息
     */
    @GET
    Observable<ResultSwitch> getSwitch(@Url String url);


}
