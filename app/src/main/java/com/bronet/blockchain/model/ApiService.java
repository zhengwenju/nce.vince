package com.bronet.blockchain.model;


import com.bronet.blockchain.adapter.Feedback;
import com.bronet.blockchain.data.AppModel;
import com.bronet.blockchain.data.AppNode;
import com.bronet.blockchain.data.Article;
import com.bronet.blockchain.data.Assets;
import com.bronet.blockchain.data.AssetsFromCoin;
import com.bronet.blockchain.data.Assure;
import com.bronet.blockchain.data.Attendance;
import com.bronet.blockchain.data.Banlance;
import com.bronet.blockchain.data.Banner;
import com.bronet.blockchain.data.BuyMine;
import com.bronet.blockchain.data.CApp;
import com.bronet.blockchain.data.Candles1000;
import com.bronet.blockchain.data.CenterActivit;
import com.bronet.blockchain.data.CoinDataModel;
import com.bronet.blockchain.data.CoinDetail;
import com.bronet.blockchain.data.CoinTypeResp;
import com.bronet.blockchain.data.Confirm;
import com.bronet.blockchain.data.ContractNum;
import com.bronet.blockchain.data.ContractRules;
import com.bronet.blockchain.data.Credits;
import com.bronet.blockchain.data.CustomCoin;
import com.bronet.blockchain.data.DataBean;
import com.bronet.blockchain.data.DetailsBean;
import com.bronet.blockchain.data.DeviceModel;
import com.bronet.blockchain.data.GetCoinDetail1;
import com.bronet.blockchain.data.GetH24List;
import com.bronet.blockchain.data.GetPrice;
import com.bronet.blockchain.data.IdeResult;
import com.bronet.blockchain.data.IdentiResult1;
import com.bronet.blockchain.data.IdentiResult2;
import com.bronet.blockchain.data.Info;
import com.bronet.blockchain.data.InvestLevelList;
import com.bronet.blockchain.data.InvestList;
import com.bronet.blockchain.data.InvestNum;
import com.bronet.blockchain.data.InvitedList;
import com.bronet.blockchain.data.Login;
import com.bronet.blockchain.data.Mine;
import com.bronet.blockchain.data.Mode13;
import com.bronet.blockchain.data.Model;
import com.bronet.blockchain.data.Model1;
import com.bronet.blockchain.data.Model10;
import com.bronet.blockchain.data.MutualModelList;
import com.bronet.blockchain.data.MyMineInfo;
import com.bronet.blockchain.data.MyMineList;
import com.bronet.blockchain.data.MyNodeList;
import com.bronet.blockchain.data.MyTradeList;
import com.bronet.blockchain.data.MyTradeListSale;
import com.bronet.blockchain.data.MyTransfer;
import com.bronet.blockchain.data.NCEList;
import com.bronet.blockchain.data.NceEthRange;
import com.bronet.blockchain.data.NodeInfo;
import com.bronet.blockchain.data.NodeList;
import com.bronet.blockchain.data.NodeLog;
import com.bronet.blockchain.data.PkpModel;
import com.bronet.blockchain.data.PkpModel2;
import com.bronet.blockchain.data.PrizeRecordModel;
import com.bronet.blockchain.data.Progress;
import com.bronet.blockchain.data.ReInvests;
import com.bronet.blockchain.data.RecallList;
import com.bronet.blockchain.data.ReceModel;
import com.bronet.blockchain.data.RechargeRecordBean;
import com.bronet.blockchain.data.Register;
import com.bronet.blockchain.data.RegisterS;
import com.bronet.blockchain.data.RemindList;
import com.bronet.blockchain.data.ResAssure;
import com.bronet.blockchain.data.Tickers;
import com.bronet.blockchain.data.TokenModel;
import com.bronet.blockchain.data.TradeBuyList;
import com.bronet.blockchain.data.TradeBuyWarning;
import com.bronet.blockchain.data.TradeList;
import com.bronet.blockchain.data.TradeNce;
import com.bronet.blockchain.data.TradeSellList;
import com.bronet.blockchain.data.TransCoins;
import com.bronet.blockchain.data.TransSell;
import com.bronet.blockchain.data.TransferAgreement;
import com.bronet.blockchain.data.TransferMineLog;
import com.bronet.blockchain.data.TransferMineReward;
import com.bronet.blockchain.data.TransferNodeLog;
import com.bronet.blockchain.data.TransferNodeReward;
import com.bronet.blockchain.data.TransferRewardLog;
import com.bronet.blockchain.data.TrigerResultModel;
import com.bronet.blockchain.data.Type;
import com.bronet.blockchain.data.TypeList;
import com.bronet.blockchain.data.UserInfo;
import com.bronet.blockchain.data.UserMineLog;
import com.bronet.blockchain.data.Username;
import com.bronet.blockchain.data.VerifyPwd;
import com.bronet.blockchain.data.WinningList;
import com.bronet.blockchain.data.WithdrawLog;
import com.bronet.blockchain.data.WithdrawLog2;
import com.bronet.blockchain.data.Withdrawal;
import com.bronet.blockchain.data.Withdrawal2;
import com.bronet.blockchain.ui.CenterActivity;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;


/**
 * retrofit api 接口类
 *
 * @packageName: cn.white.ymc.wanandroidmaster.Model.api
 * @fileName: ApiServer
 * @date: 2018/7/24  10:45
 * @author: ymc
 * @QQ:745612618
 */

public interface ApiService {
    /**
     * 注册
     */
    @POST("/api/Auth/register")
    Observable<DataBean<RegisterS>> register(@Body RequestBody requestBody);
    /**
     * 登录
     */
    @POST("/api/Auth/login")
    Observable<Login> login(@Body RequestBody requestBody);

    /**
     *通过邀请码获取邀请人编号
     * @param inviteCode
     * @return
     */
    @GET("/api/Auth/IdByCode")
    Observable<Register>improveRepair(@Query("inviteCode") String inviteCode);

    /**
     *24H涨幅榜
     * @return
     */
    @GET("/api/Markets/DayChangeList")
    Observable<GetH24List>GetH24List();
    /**
     *24H涨幅榜(成交量)
     * @return
     */
    @GET("/api/Markets/DayVolumeList")
    Observable<GetH24List>GetH24Volume();

    /**
     *我的好友列表
     * @return
     */
    @GET("/api/User/InvitedList")
    Observable<InvitedList>InvitedList(@Query("userid") String userid);

    /**
     *轮播图
     * @return
     */
    @GET("/api/Banner")
    Observable<Banner>Banner();
    /**
     *用户基本信息
     * @return
     */
    @GET("/api/User/info")
    Observable<UserInfo>info(@Query("id") String id);
    /**
     *上传图片(返回图片相对路径)
     * @return
     */
    @GET("api/File/Upload")
    Call<UserInfo>Upload(@Query("id") String id);

    /**获取APP端走马灯广告
     * @return
     */
    @GET("/api/Article")
    Observable<Article>Article();
    /**
     * 修改昵称
     * @return
     */
    @POST("/api/User/username")
    Observable<Username>username(@Body RequestBody requestBody);
    /**
     * 修改头像
     * @return
     */
    @POST("/api/User/avatar")
    Observable<Username>avatar(@Body RequestBody requestBody);
    /**
     * 获取会员信用
     * @return
     */
    @GET("/api/User/Credits")
    Observable<Credits>Credits(@Query("userid") String userid);
    /**
     * 新币榜
     * @return
     */
    @GET("/api/Markets/NewCoinList   ")
    Observable<GetH24List>GetCoinList();
    /**
     * 我的合约列表
     * @return
     */
    @GET("/api/Invest/List")
    Observable<InvestList>InvestList(@Query("userid") String userid, @Query("pageNo") int pageNo, @Query("pageSize") int pageSize);
    /**
     * 合约规则
     * @return
     */
    @GET("/api/Message/ContractRules")
    Observable<ContractRules>ContractRules();
    /**
     *风险提示
     * @return
     */
    @GET("/api/Message/RiskWarning")
    Observable<ContractRules>RiskWarning();
    /**
     * 复投合约收益
     * @return
     */
    @GET("/api/Invest/ReInvestReward")
    Observable<ContractNum>ReInvestReward(@Query("userid") String userid);
    /**
     *合约数量
     * @return
     */
    @GET("/api/Invest/InvestNum")
    Observable<ContractNum>InvestNum(@Query("userid") String userid);
    /**
     *合约帐户总收益
     * @return
     */
    @GET("/api/Invest/InvestReward")
    Observable<ContractNum>InvestReward(@Query("userid") String userid);
    /**
     *通过私钥登录
     * @return
     */
    @POST("/api/Auth/loginkey")
    Observable<Login>loginkey(@Body RequestBody requestBody);
    /**
     * 获取货币行情(所有货币信息,通过货币名称筛选)
     * @return
     */
    @GET("/api/Coin/Tickers")
    Observable<Tickers>Tickers(@Query("coinName") String coinName);
    /**
     *获取用户自定义货币行情
     * @return
     */
    @GET("/api/Coin/CustomTickers")
    Observable<Tickers>CustomTickers(@Query("userid") String userid);
    /**
     *获取资产
     * @return
     */
    @GET("/api/UserCoin/Assets")
    Observable<Assets>Assets(@Query("userid") String userid);
    /**
     *获取全部行情标签的筛选项
     * @return
     */
    @GET("/api/Message/CustomCoin")
    Observable<CustomCoin>CustomCoin();
    /**
     *获取自定义行情筛选项
     * @return
     */
    @GET("/api/User/CustomCoin")
    Observable<CustomCoin>CustomCoins(@Query("userid") String userid);
    /**
     *提币
     * @return
     */
    @POST("/api/UserCoin/withdrawal")
    Observable<Withdrawal2>withdrawal(@Body RequestBody requestBody);
    /**
     *开通合约
     * @return
     */
    @POST("/api/Invest/Investment")
    Observable<Withdrawal>Investment(@Body RequestBody requestBody);

    /**
     * 可用余额
     * @param userid
     * @param coidId  货币编号1.ETH,2.BTC,3.NCE
     * @return
     */
    @GET("/api/UserCoin/Banlance" )
    Observable<Banlance>Banlance(@Query("userid") String userid, @Query("coinId") String coidId);
    /**
     *获取价格
     * @return
     */
    @GET("/api/Coin/GetPrice" )
    Observable<GetPrice>GetPrice();
    /**
     *获取会员提币记录
     * @return
     */
    @GET("/api/User/WithdrawLog")
    Observable<WithdrawLog>WithdrawLog(@Query("userId") String userId, @Query("pageNo") int pageNo, @Query("pageSize") int pageSize);
    /**
     *获取合约收益记录
     * @return
     */
    @GET("/api/Invest/RewardLog")
    Observable<InvestList>RewardLog(@Query("userId") String userId, @Query("pageNo") int pageNo, @Query("pageSize") int pageSize);
//    /**
//     *复投
//     * @return
//     */
//    @POST("/api/Invest/ReInvests")
//    Observable<ReInvests>ReInvests(@Body RequestBody requestBody);
//    /**
//     *取消自动复投
//     * @return
//     */
//    @POST("/api/Invest/CancelAutoRepetition")
//    Observable<ReInvests>CancelAutoRepetition(@Body RequestBody requestBody);

    /**
     *取消自动复投
     * @return
     */
    @POST("/api/Invest/AutoRepetition")
    Observable<ReInvests>AutoRepetition(@Body RequestBody requestBody);

    /**
     *已回款
     * @return
     */
    @POST("/api/Invest/ReturnReInvest")
    Observable<ReInvests>ReturnReInvest(@Body RequestBody requestBody);
    /**
     *NCE:ETH比率 例如 1000:1=1000
     * @return
     */
    @GET("/api/Message/NceEthRange")
    Observable<NceEthRange>NceEthRange();

    /**
     *NCE:ETH比率 例如 1000:1=1000
     * @return
     */
    @GET("/api/Message/EthNceRange")
    Observable<NceEthRange>EthNceRange();

    /**
     *NCE:ETH比率 例如 1000:1=1000
     * @return
     */
    @GET("/api/Message/Range")
    Observable<NceEthRange>Range(@Query("inCoinType") int inCoinType, @Query("outCoinType") int outCoinType);
    /**
     *NCE兑换(0.买入 1.卖出)
     * @return
     */
    @POST("/api/Trade/TradeNce")
    Observable<TradeNce>TradeNce(@Body RequestBody requestBody);
    /**
     *每日签到
     * @return
     */
    @GET("/api/User/attendance")
    Observable<Attendance>attendance(@Query("userid") String userid);
    /**
     *添加反馈
     * @return
     */
    @POST("/api/Feedback/Add")
    Observable<Attendance>Add(@Body RequestBody requestBody);
    /**
     *获取反馈信息
     * @return
     */
    @GET("/api/Feedback")
    Observable<Feedback>Feedback(@Query("userId") String userId, @Query("pageNo") int pageNo);
    /**
     *获取文章分类
     * @return
     */
    @GET("/api/Article/Type")
    Observable<Type>Type(@Query("version") String version);
    /**
     *获取文章列表
     * @return
     */
    @POST("/api/Article/List")
    Observable<TypeList>TypeList(@Body RequestBody requestBody);
    /**
     *获取文章详情
     * @return
     */
    @GET("/api/Article/Info")
    Observable<Info>Info(@Query("id") int id);
    /**
     用户当前进程(0.非法用户,1.用户没有充币记录,2.用户没有交易记录,3.用户没有合约记录,4.有合约记录)
     * @return
     */
    @GET("/api/User/Progress")
    Observable<Progress>Progress(@Query("userid") String userid);
    /**
     * NCE兑换记录
     * @return
     */
    @GET("/api/Trade/List")
    Observable<NCEList>NCEList(@Query("userid") String userid);
    /**
     *交易买提示
     * @return
     */
    @GET("/api/Message/TradeBuyWarning")
    Observable<TradeBuyWarning>TradeBuyWarning();
    /**
     *交易卖提示
     * @return
     */
    @GET("/api/Message/TradeSellWarning")
    Observable<TradeBuyWarning>TradeSellWarning();
    /**
     *社区人数
     * @return
     */
    @GET("/api/User/TeamNum")
    Observable<TradeBuyWarning>TeamNum(@Query("userid") String userid);
    /**
     *获取邀请人数
     * @return
     *//*
    @GET("/api/User/InviteNum")
    Observable<TradeBuyWarning>InviteNum(@Query("userid") String userid);*/
    /**
     * 提币提示
     * @return
     */
    @GET("/api/Message/WithdrawWarning")
    Observable<TradeBuyWarning>WithdrawWarning(@Query("coinId") int coinId);
    /**
     *充值提示
     * @return
     */
    @GET("/api/Message/RechargeWarning")
    Observable<TradeBuyWarning>RechargeWarning();

    /**
     *充值提示
     * @return
     */
    @GET("/api/Message/RechargeWarning")
    Observable<TradeBuyWarning>RechargeWarning(@Query("coinId") int coinId);
    /**
     *充值(返回地址)
     * @return
     */
    @POST("/api/UserCoin/recharge")
    Observable<TradeBuyWarning>recharge(@Body RequestBody requestBody);
    /**
     *担保额度
     * @return
     */
    @GET("/api/Vouch/Amount")
    Observable<Assure>GetAssureQuota();
    /**
     *预约明细
     * @return
     */
    @GET("/api/Vouch/List")
    Observable<InvestList>SubscribeLog(@Query("userid") String userid);
    /**
     *担保明细
     * @return
     */
    @GET("/api/Vouch/VouchList")
    Observable<InvestList>AssureLog(@Query("userid") String userid);
    /**
     *接受担保
     * @return
     */
    @POST("/api/Vouch/AddVouchMoney")
    Observable<ResAssure>AssureAccept(@Body RequestBody requestBody);
    /**
     *创建新担保金申请
     * @return
     */
    @POST("/api/Vouch/CreateApp")
    Observable<CApp>CreateApp(@Body RequestBody requestBody);
    /**
     *测试担保信息是否有效
     * @return
     */
    @POST("/api/Vouch/TestBzMoney")
    Observable<ResAssure>TestBzMoney(@Body RequestBody requestBody);

    /**
     *预约明细
     * @return
     */
    @GET("/api/Vouch/List")
    Observable<InvestList>Appointment(@Query("userid") String userid);

    /**
     *担保明细
     * @return
     */
    @GET("/api/Vouch/VouchList")
    Observable<InvestList>Guarantee(@Query("userid") String userid);
    /**
     *预约详情
     * @return
     */
    @GET("/api/Vouch/Detail")
    Observable<DetailsBean>AppointmentDetails(@Query("appId") int appId);
    /**
     *充值记录
     * @return
     */
    @GET("/api/User/RechargeLog")
    Observable<RechargeRecordBean>RechargeRecord(@Query("userId") String userId, @Query("pageNo") int pageNo, @Query("pageSize") int pageSize);

    /**
     *检测APP更新
     * @return
     */
    @POST("/api/App/CheckNewVersion")
    Observable<AppModel> CheckNewVersion(@Body RequestBody requestBody);

    /**
     *Usdt转Nce
     * @return
     */
    @GET("/api/Message/Usdt2Nce")
    Observable<Model> Usdt2Nce(@Query("usdPrice") String usdPrice);


    /**
     * @return
     */
    @POST("/api/Values/CreateToken")
    Observable<TokenModel> CreateToken(@Body RequestBody requestBody);


    /**
     * @return
     */
    @GET("/api/Values")
    Observable<Object> Values();

    /**
     * 获取推荐收益余额
     * @return
     */
    @GET("/api/User/RecommendReward")
    Observable<ContractNum> RecommendReward(@Query("userid") String userid);

    /**
     *划转推荐奖金到用户余额
     * @return
     */
    @POST("/api/User/KitingRecommendReward")
    Observable<Model1>KitingRecommendReward(@Body RequestBody requestBody);
    /**
     *
     * 认筹套餐列表
     * @return
     */
    @GET("/api/UserRenChou/RenChouMealList")
    Observable<IdentiResult1>RenChouMealList(@Query("userid") String userid);
    /**
     *
     * 认筹套餐列表
     * @return
     */
    @GET("/api/UserRenChou/RenChouMeal")
    Observable<IdentiResult2>RenChouMeal(@Query("taocan") int taocan);
    /**
     *
     * 认筹协议
     * @return
     */
    @GET("/api/Message/RenChouAgreement")
    Observable<IdeResult>RenChouAgreement();

    /**
     *
     * 认筹提示
     * @return
     */
    @GET("/api/Message/RenChouWarning")
    Observable<IdeResult>RenChouTip();

    /**
     *认筹
     * @return
     */
    @POST("/api/UserRenChou/RenChou")
    Observable<IdeResult>RenChou(@Body RequestBody requestBody);


    /**
     * @return 是否认筹
     */
    @GET("/api/UserRenChou/IsRenChou")
    Observable<CApp> IsRenChou(@Query("userid") String userid);


    /**
     * 转让协议
     */
    @GET("/api/Message/TransferAgreement")
    Observable<TransferAgreement> TransferAgreement();


    /**
     *转让
     * @return
     */
    @POST("/api/UserRenChou/Transfer")
    Observable<IdeResult>Transfer(@Body RequestBody requestBody);

    /**
     * @转让记录
     */
    @GET("/api/UserRenChou/list")
    Observable<MyTransfer> TransferList(@Query("userid") String userid);
    /**
     * @接收人确认认筹操作
     */
    @POST("/api/UserRenChou/Confirm")
    Observable<Confirm> Confirm(@Body RequestBody requestBody);

    /**
     * @
     获取接收人信息
     */
    @GET("/api/UserRenChou/Receiver")
    Observable<ReceModel> Receiver(@Query("userid") String userid);



    /*





     */
/**
 * @
 卖出
 *//*

    @POST("/api/UserEntrust/Sell")
    Observable<TransSell> Sell(@Body RequestBody requestBody);
    */
/**
 * @
 买入
 *//*

    @POST("/api/UserEntrust/Buy")
    Observable<TransSell> Buy(@Body RequestBody requestBody);



    */
/**
 委托列表
 *//*

    @GET("/api/UserEntrust/List")
    Observable<TranEntrusts> AllList(@Query("userid")String userid,@Query("inCoinType")int coinId,@Query("outCoinType")int payCoinId);

    */
/**
 我的委托列表
 *//*

    @GET("/api/UserEntrust/MyList")
    Observable<TranEntrusts> MyList(@Query("userid")String userid,@Query("inCoinType")int coinId,@Query("outCoinType")int payCoinId);





    */
/**
 我的委托列表
 *//*

    @GET("/api/UserEntrust/TradeList")
    Observable<TranEntrusts> TradeList(@Query("userid")String userid,@Query("inCoinType")int inCoinType);

    */
/**
 * @
 撤回
 *//*

    @POST("/api/UserEntrust/ReCall")
    Observable<RecallList> ReCall(@Body RequestBody requestBody);
*/



    /**
     委托货币兑换列表
     */
    @GET("/api/Trade/EntrustChange")
    Observable<TransCoins> EntrustChange();


    /**
     * @
     买入后上架销售
     */
    @POST("/api/DelegateBuy/OnSell")
    Observable<TransSell> UserBuyOnSell(@Body RequestBody requestBody);

    /**
     * @
     买入记录
     */
    @GET("/api/DelegateBuy/TradeList")
    Observable<TradeList> TradeBuyList(@Query("outCoinType") int inCoinType, @Query("inCoinType") int outCoinType);
    /**
     * @
     买入全部委托列表
     */
    @GET("/api/DelegateBuy/List")
    Observable<TradeBuyList> TradeBuyAllList(@Query("userid") String userid, @Query("inCoinType") int inCoinType, @Query("outCoinType") int outCoinType, @Query("pageNo") int pageNo, @Query("pageSize") int pageSize, @Query("orderName") String orderName, @Query("orderby") int orderby);
    /**
     * @
     买入我的委托列表
     */
    @GET("/api/DelegateBuy/MyList")
    Observable<TradeBuyList> TradeBuyMyList(@Query("userid") String userid, @Query("inCoinType") int inCoinType, @Query("outCoinType") int outCoinType);

    /**
     * @
     买入撤回
     */
    @POST("/api/DelegateBuy/ReCall")
    Observable<RecallList> BuyReCall(@Body RequestBody requestBody);
    /**
     * @
     列表中的买入
     */
    @POST("/api/DelegateBuy/Buy")
    Observable<TransSell> BuyInlist(@Body RequestBody requestBody);


    /**
     * @
     上架卖出NCE
     */
    @POST("/api/DelegateSale/OnSell")
    Observable<TransSell> UserSellOnSell(@Body RequestBody requestBody);
    /**
     * @
     卖出记录
     */
    @GET("/api/DelegateSale/TradeList")
    Observable<TradeSellList> TradeSellList(@Query("outCoinType") int inCoinType, @Query("inCoinType") int outCoinType);

    /**
     * @
     卖出全部委托列表
     */
    @GET("/api/DelegateSale/List")
    Observable<TradeBuyList> TradeSellAllList(@Query("userid") String userid, @Query("inCoinType") int inCoinType, @Query("outCoinType") int outCoinType, @Query("pageNo") int pageNo, @Query("pageSize") int pageSize, @Query("orderName") String orderName, @Query("orderby") int orderby);
    /**
     * @
     卖出我的委托列表
     */
    @GET("/api/DelegateSale/MyList")
    Observable<TradeBuyList> TradeSellMyList(@Query("userid") String userid, @Query("inCoinType") int inCoinType, @Query("outCoinType") int outCoinType);
    /**
     * @
     卖出撤回
     */
    @POST("/api/DelegateSale/ReCall")
    Observable<RecallList> SellReCall(@Body RequestBody requestBody);

    /**
     * @
     列表中的卖出
     */
    @POST("/api/DelegateSale/Sell")
    Observable<TransSell> SellInList(@Body RequestBody requestBody);
    /**
     * @
     修改密码
     */
    @POST("/api/Auth/EditPassword")
    Observable<Model10> EditPassword(@Body RequestBody requestBody);


    /**
     获取设备列表
     */
    @GET("/api/UserDevice/List")
    Observable<DeviceModel> deviceList(@Query("userid") String userid);

    /**
     * 删除设备
     */
    @POST("/api/UserDevice/Del")
    Observable<Model10> deviceDelete(@Body RequestBody requestBody);

    /**
     * 设置设备
     */
    @POST("/api/UserDevice/SetDefault")
    Observable<Model10> setDefault(@Body RequestBody requestBody);

    /**
     获取提币数据
     */
    @GET("/api/WithdrawAddress/List")
    Observable<CoinDataModel> withdrawAddressList(@Query("userid") String userid, @Query("coinId") int coinId);


    /**
     获取基本货币信息
     */
    @GET("/api/Coin/BaseList")
    Observable<CoinDetail> BaseList();

    /**
     兑换手续费
     */
    @GET("/api/Message/ExchangeNceFee")
    Observable<Model10> ExchangeNceFee();


    /**
     * 增加提币地址
     */
    @POST("/api/WithdrawAddress/Save")
    Observable<Model10> SaveWithdrawAddress(@Body RequestBody requestBody);


    /**
     * @
     我的卖出记录
     */
    @GET("/api/DelegateBuy/MyTradeList")
    Observable<MyTradeList> MyTradeListBuy(@Query("outCoinType") int inCoinType, @Query("inCoinType") int outCoinType,@Query("userid") String userid, @Query("period") int period);
    /**
     * @
     我的买入记录
     */
    @GET("/api/DelegateSale/MyTradeList")
    Observable<MyTradeListSale> MyTradeListSale(@Query("outCoinType") int inCoinType, @Query("inCoinType") int outCoinType,@Query("userid") String userid, @Query("period") int period);

    /**
     * 通过助记词绑定设备
     */
    @POST("/api/UserDevice/Mnemonickeys")
    Observable<Login> Mnemonickeys(@Body RequestBody requestBody);

    /**
     * 通过私钥绑定设备
     */
    @POST("/api/UserDevice/PrivateKey")
    Observable<Login> PrivateKey(@Body RequestBody requestBody);


    /**
     是否操作冻结
     */
    @GET("/api/User/OperationFreeze")
    Observable<Model1> OperationFreeze(@Query("userid") String userid);


//    /**
//     是否操作冻结
//     */
//    @GET("/api/Message/Language")
//    Observable<Model1> Language();


//    /**
//     获取投资货币列表
//     */
//    @GET("/api/Coin/InvestCoin")
//    Observable<Mode12> InvestCoin();

    /**
     * 开始抽奖
     */
    @POST("/api/Lottery/Lottery")
    Observable<Mode13> Lottery(@Body RequestBody requestBody);
    /**
     * 抽奖收益
     */
    @POST("/api/Lottery/RewardQty")
    Observable<Mode13> RewardQty(@Body RequestBody requestBody);
    /**
     * @
     获取抽奖记录
     */
    @GET("/api/Lottery/List")
    Observable<PrizeRecordModel> LotteryList(@Query("userId") String userid, @Query("pageNo") int pageNo, @Query("pageSize") int pageSize, @Query("gameId") int gameId);

    /**
     * 游戏协议
     */
    @GET("/api/Message/GameLotteryRules")
    Observable<TransferAgreement> GameLotteryRules();
    /**
     * 中奖列表(跑马灯显示)
     */
    @GET("/api/Lottery/WinningList")
    Observable<WinningList> WinningList(@Query("gameId") int gameId);

    /**
     *获取用户401错误提示
     * @return
     */
    @POST("/api/Auth/VerifyUserPolicy")
    Observable<Model10>VerifyUserPolicy(@Body RequestBody requestBody);

    /**
     *今天ETH提币限制
     * @return
     */
    @POST("/api/UserCoin/TodayWithdrawLimit")
    Observable<Model10>TodayWithdrawLimit(@Body RequestBody requestBody);

    /**
     * 翻牌()
     */
    @GET("/api/Lottery/QtyList")
    Observable<PkpModel> QtyList();

    /**
     *今天ETH提币限制
     * @return
     */
    @POST("/api/Lottery/CardDraw")
    Observable<PkpModel2>CardDraw(@Body RequestBody requestBody);

    /**
     * 获取申请列表
     */
    @GET("/api/Mutual/List")
    Observable<MutualModelList> MutualList(@Query("userId") String userId, @Query("pageNo") int pageNo, @Query("pageSize") int pageSize);

    /**
     * @
     (添加申请)重置密码
     */
    @POST("/api/Mutual/Add")
    Observable<Model10> MutualAdd(@Body RequestBody requestBody);

    /**
     * @
     审核申请
     */
    @POST("/api/Mutual/Audit")
    Observable<Model10> MutualAudit(@Body RequestBody requestBody);


    /**
     * 是否存在互助申请
     */
    @GET("/api/Mutual/ExistApp")
    Observable<Model10> ExistApp(@Query("userId") String userId);

    /**
     * 是否存在互助申请
     */
    @GET("/api/Mutual/WaitAudit")
    Observable<Model10> WaitAudit(@Query("userId") String userId);


    /**
     * 撤消提交
     */
    @GET("/api/Mutual/ReCall")
    Observable<Model10> ReCall(@Query("userId") String userId);

    /**
     * 可用提币额度限制
     */
    @GET("/api/User/TransferQty")
    Observable<ContractNum> AmountLimit(@Query("userId") String userId);


    /**
     * 可用额度调整日志
     */
    @GET("/api/User/AdjustmentAmountLog")
    Observable<WithdrawLog2> AdjustmentAmountLog(@Query("userId") String userId, @Query("pageNo") int pageNo, @Query("pageSize") int pageSize);


    /**
     * 获取投资范围列表(NCE)
     */
    @GET("/api/Invest/InvestLevelList")
    Observable<InvestLevelList> InvestLevelList();

    /**
     * 获取提示列表
     */
    @GET("/api/Remind/List")
    Observable<RemindList> RemindList();
    /**
     * 获取冻结原因
     */
    @GET("/api/Freeze")
    Observable<ContractNum> Freeze(@Query("userId") String userId);

    /**
     * @
     反馈是否回复
     */
    @GET("/api/Feedback/ExistAnswer")
    Observable<Model10> ExistAnswer(@Query("userId") String userId);

    /**
     * @
     设置反馈已读
     */
    @POST("/api/Feedback/SetRead")
    Observable<Model10> SetRead(@Body RequestBody requestBody);

    /**
     * @
     投币机(水果)游戏
     */
    @POST("/api/Lottery/StartFruit")
    Observable<TrigerResultModel> StartFruit(@Body RequestBody requestBody);

    /**
     * @
     用户节点日志
     */
    @GET("/api/Node/UserNodeLog")
    Observable<NodeLog> NodeLog(@Query("userId") String userId,@Query("userNodeId") int  userNodeId,@Query("pageNo") int pageNo, @Query("pageSize") int pageSize);
    /**
     * @
     全部节点列表
     */
    @GET("/api/Node")
    Observable<NodeList> NodeList(@Query("userId") String userId);
    /**
     * @
     我的节点详情
     * @param
     */
    @GET("/api/Node/MyNode")
    Observable<NodeInfo> NodeInfo(@Query("id") int  id,@Query("userId") String userId);
    /**
     * @
     购买节点
     */
    @POST("/api/Node/BuyNode")
    Observable<AppNode> AppNode(@Body RequestBody requestBody);

    /**
     *划转节点收益
     * @return
     */
    @POST("/api/Node/TransferNodeReward")
    Observable<TransferNodeReward>TransferNodeReward(@Body RequestBody requestBody);

    /**
     * @
     我的节点
     * @param
     */
    @GET("/api/Node/MyNodeList")
    Observable<MyNodeList> MyNodeList(@Query("userId") String userId);

    /**
     * @
     获取划转日志
     * @param
     */
    @GET("/api/Node/TransferNodeLog")
    Observable<TransferNodeLog> TransferNodeLog(@Query("userId") String userId,@Query("userNodeId") int  userNodeId,@Query("pageNo") int pageNo, @Query("pageSize") int pageSize);
    /**
     * @
     获取会员推荐收益划转记录
     * @param
     */
    @GET("/api/User/TransferRecommendRewardLog")
    Observable<TransferRewardLog> TransferRecommendRewardLog(@Query("userId") String userId, @Query("pageNo") int pageNo, @Query("pageSize") int pageSize);



    @POST("/api/Instruments/Candles")
    Observable<String[][]>Candles(@Body RequestBody requestBody);


    @POST("/api/Instruments/Candles1000")
    Observable<Candles1000>Candles1000(@Body RequestBody requestBody);

    /**
     * @
     验证密码
     * @param
     */
    @POST("/api/User/VerifyPwd")
    Observable<VerifyPwd> VerifyPwd(@Body RequestBody requestBody);

    /**
     * @
     合约数量(NCE)
     * @param
     */
    @GET("/api/Summary/InvestNum")
    Observable<InvestNum> InvestNum2(@Query("userId") String userId);
    /**
     * @
     委托买入数量(NCE)
     * @param
     */
    @GET("/api/Summary/DelegateBuy")
    Observable<InvestNum> DelegateBuy(@Query("userId") String userId);
    /**
     * @
     委托卖出数量(NCE)
     * @param
     */
    @GET("/api/Summary/DelegateSale")
    Observable<InvestNum> DelegateSale(@Query("userId") String userId);

    /**
     *
     * 获取币种详情
     * @param
     */
    @GET("/api/Markets/CoinDetail")
    Observable<GetCoinDetail1> CoinDetail1(@Query("coinName") String coinName);




    /**
     * @
     */
    @GET("/api/Markets/CoinDetail")
    Observable<CoinTypeResp> CoinDetail(@Query("coinName") String coinName);
    /**
     * @
     * 活动中心
     */
    @GET("/api/Article/Activity")
    Observable<CenterActivit> Activity();

    /**
     * @
     * 全部矿机
     */
    @GET("/api/Mine")
    Observable<Mine> Mine();
    /**
     * @
     * 购买矿机
     */
    @POST("/api/Mine/BuyMine")
    Observable<BuyMine> BuyMine(@Body RequestBody requestBody);
    /**
     * @
     * 我的矿机
     */
    @GET("/api/Mine/MyMineList")
    Observable<MyMineList> MyMineList(@Query("userId") String userId);

    /**
     * @
     我的矿机详情
     * @param
     */
    @GET("/api/Mine/MyMine")
    Observable<MyMineInfo> MyMineInfo(@Query("id") int  id, @Query("userId") String userId);

    /**
     * @
     用户矿机日志
     */
    @GET("/api/Mine/UserMineLog")
    Observable<UserMineLog> UserMineLog(@Query("userId") String userId, @Query("userMineId") int  userMineId, @Query("pageNo") int pageNo, @Query("pageSize") int pageSize);

    /**
     *划转矿机收益
     * @return
     */
    @POST("/api/Mine/TransferMineReward")
    Observable<TransferMineReward>TransferMineReward(@Body RequestBody requestBody);

    /**
     * @
     获取会员推荐收益划转记录
     * @param
     */
    @GET("/api/Mine/TransferMineLog")
    Observable<TransferMineLog> TransferMineLog(@Query("userId") String userId, @Query("userNodeId") int  userNodeId,@Query("pageNo") int pageNo, @Query("pageSize") int pageSize);





    /**
     * 谷歌广告统计
     * @return
     */
    @GET("/api/Ad/Success")
    Observable<AppNode> GoogleAdSuccess(@Query("userId") String userId);
    @GET("/api/Ad/Fail")
    Observable<AppNode> GoogleAdFail(@Query("userId") String userId);

    //我的基本资产
    @GET("/api/UserCoin/AssetsFromCoin")
    Observable<AssetsFromCoin> AssetsFromCoin(@Query("userId") String userId,@Query("coinId") int coinId);

    //是否为合约关闭期
    @GET("/api/Invest/IsClosePeriod")
    Observable<AppNode> IsClosePeriod();



}