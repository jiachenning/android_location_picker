百度地图Android SDK自v2.3.0起，采用可定制的形式为您提供开发包，当前开发包包含如下功能:
基础地图：地图显示、操作、个性化地图切换和各种覆盖物图层：
检索功能：POI检索、地理编码、路径规划等；
LBS云检索：基于LBS云的周边、城市内、区域、详情检索；
计算工具：调启百度地图客户端导航、poi检索、poi点全景展示、调启Web App导航、计算距离等；
周边雷达：包含位置信息上传和检索周边相同应用的用户位置信息功能；

基础地图：地图显示、操作和各种覆盖物图层：

检索功能：POI检索、地理编码、路径规划等；

LBS云检索：基于LBS云的周边、城市内、区域、详情检索；

计算工具：调启百度地图客户端导航、调启Web App导航、计算距离等；

周边雷达：包含位置信息上传和检索周边相同应用的用户位置信息功能；

------------------------------------------------------------------------------------------------

Android 地图 SDK v4.1.0是适用于Android系统移动设备的矢量地图开发包

------------------------------------------------------------------------------------------------
注意：为了给用户提供更安全的服务，Android SDK自v2.1.3版本开始采用了全新的Key验证体系。因此，当您选择使用v2.1.3及之后版本的SDK时，需要到新的Key申请页面进行全新Key的申请，申请及配置流程请参考开发指南的对应章节。（选择使用v2.1.2及之前版本SDK的开发者，申请密钥（Key） 的方式不变）
------------------------------------------------------------------------------------------------
地图SDK功能介绍（全功能开发包）：

地图：
	提供地图（2D、3D、室内图）的展示和缩放、平移、旋转、改变视角等地图操作；个性化地图展示、个性化地图与普通地图类型动态切换
POI检索：
	可根据关键字，对POI数据进行周边、区域和城市内三种检索，支持poi室内检索；
地理编码：
	提供地理坐标和地址之间相互转换的能力；
线路规划：
	支持公交信息查询、公交换乘查询、驾车线路规划和步行路径检索；
覆盖物：
	提供多种地图覆盖物（瓦片图层、自定义标注、几何图形、文字绘制、地形图图层、热力图图层等），满足开发者的各种需求；
定位：
	采用多种定位模式，使用定位SDK获取位置信息，使用地图SDK我的位置图层进行位置展示；
离线地图：
	支持使用离线地图，节省用户流量，同时为用户带来更好的地图体验；
调启百度地图：
	利用SDK接口，直接在本地打开百度地图客户端或WebApp，实现地图功能。
周边雷达：
	利用周边雷达功能，开发者可在App内低成本、快速实现查找周边使用相同App的用户位置的功能。
LBS云检索：
	支持用户检索存储在LBS云内的自有POI数据，并展示；
特色功能：
	提供短串分享、Place详情检索、热力图等特色功能，帮助开发者搭建功能更加强大的应用；
------------------------------------------------------------------------------------------------

【新版提示】

	
1、自v3.6.0起，官网不再支持地图离线包下载，所以SDK去掉“手动导入离线包接口”，SDK在线下载离线包接口仍维持不变。	

2、因为新版采用新的分包形式，旧包无法与新包同时混用，请将之前所有旧包（so和jar）全部替换为新包。	

3、自V3.6.0起，Android SDK使用新的矢量地图样式，地图显示更加清新，和百度地图客户端保持一致。
	

4、自V3.6.0起，原内置覆盖物相关类代码开源（OverlayManager/PoiOverlay/TransitRouteOverlay/WalkingRouteOverlay/BusLineOverlay）,源码可在BaiduMapsApiDemo工程中找到。

	
5、自V3.6.0起，Android SDK采用功能包拆分的形式，

其中:
baidumapapi_base_vX_X_X.jar和libBaiduMapSDK_base_vX_X_X.so为基础包，使用地图、检索、云检索、工具、周边雷达中任何一功能都必须包含；


baidumapapi_map_vX_X_X.jar和libBaiduMapSDK_map_vX_X_X.so为地图功能包；
baidumapapi_search_vX_X_X.jar和libBaiduMapSDK_search_vX_X_X.so为检索功能包；


baidumapapi_cloud_vX_X_X.jar和libBaiduMapSDK_cloud_vX_X_X.so为云检索功能包；
baidumapapi_util_vX_X_X.jar和libBaiduMapSDK_util_vX_X_X.so为工具功能包；


baidumapapi_radar_vX_X_X.jar和libBaiduMapSDK_radar_vX_X_X.so为周边雷达工具包；


如果您从http://lbsyun.baidu.com/sdk/download这里下载的开发包，提供给您的将所有jar打包成BaiduLBS_Android.jar。native动态库so的形式不变。


较之v4.0.0，升级功能：


【 新 增 】

  [ 基 础 地 图 ]
1.开放高清4K地图显示，无需设置

2.新增加载地图瓦片时，显示自定义颜色背景图：在个性化地图中配置

3.新增地图渲染完成的回调：OnMapRenderCallback

   回调接口：	void onMapRenderFinished();

   设置地图渲染完成回调接口：  setOnMapRenderCallbadk(OnMapRenderCallback callback);

4.MapView新增接口 setZOrderMediaOverlay，支持MapView显示在其他View上方，避免遮盖。

  [ 检 索 功 能 ]
1.新增室内路径规划

    RoutePlanSearch新增接口 walkingIndoorSearch(IndoorRoutePlanOption option), 发起室内路线规划
    	
    新增室内路线规划起终点参数类 IndoorPlanNode
    
    新增室内路线规划检索参数类  IndoorRoutePlanOption

    新增室内路线规划结果类  IndoorRouteResult

    OnGetRoutePlanResultListener 回调接口新增onGetIndoorRouteResult(IndoorRouteResult result); 获取室内路线规划结果信息

2.新增跨城综合公共交通线路规划（简称 跨城公交线路规划），原公交线路规划方法废弃，建议使用新方法。

    RoutePlanSearch新增接口 masstransitSearch(MassTransitRoutePlanOption option); 发起跨城公交线路规划
    	    
    新增跨城公交线路规划检索参数类  MassTransitRoutePlanOption

    新增跨城公交线路规划结果类  MassTransitRouteResult

    OnGetRoutePlanResultListener 回调接口新增onGetMassTransitRouteResult(MassTransitRouteResult result); 获取跨城公交线路规划结果信息


  [ LBS云 检 索  ]
1.新增云RGC功能

    CloudManager新增接口 rgcSearch(CloudRgcInfo info) 发起云RGC检索

    新增云RGC检索参数类 CloudRgcInfo

    新增云RGC检索结果类 CloudRgcResult
     
    CloudListener 回调接口新增 onGetCloudRgcResult(CloudRgcResult result, int error);获取云RGC检索结果


【 修 复 】
1.修复两个地图切换拖动手势问题

2.修复未安装百度地图时，调起客户端全景crash问题

3.修复骑行路线BikingRouteLine使用Intent传递时的错误问题。

4.修复new MapView时传入错误Context的crash问题

