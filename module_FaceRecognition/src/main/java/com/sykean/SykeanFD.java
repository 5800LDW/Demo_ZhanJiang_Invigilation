package com.sykean;
public class SykeanFD{
	static {
		System.loadLibrary("SykeanFD");
	}
    /**GetExtraInfo
     *获取算法详细信息
     * @param infoFlag		version
     * @param returnInfo	返回值算法的sdk版本号 64byte
     * @return FME_OK：成功
     */
    public native int GetExtraInfo(String infoFlag,byte[] returnInfo);

    /**CreateIns
     *初始化人脸算法环境
     * @param handleFace    返回算法实例 长度long[4]
     * @param strModelDir	传入脸模型文件的绝对路径末尾加/ 如/sdcard/config/
     * @param pkgName	    调用包的包名称 如示例 "com.example.syka"
     * @return FME_OK：成功
     */
    public native int CreateIns(long[] handleFace,String strModelDir,String pkgName);

    /**GetFeatureLen
     *获取当前库的人脸特征长度
     * @param hFaceHandler    算法实例 长度long[4]
     * @return 128
     */
    public native int GetFeatureLen(long[] hFaceHandler);

    /**DestroyIns
     *释放人脸算法环境
     * @param hFaceHandler    算法实例 长度long[4]
     * @return FME_OK：成功
     */
    public native int DestroyIns(long[] hFaceHandler);

    /**SetParams
     *设置检测参数
     * @param hFaceHandler      算法实例 长度long[4]
     * @param scale	            缩放比例,默认 1.2
     * @param min_neighbors	    人脸区域最小面积,默认 0.8
     * @param min_object_width	检测区域最小宽度,默认 64
     * @param max_object_width	检测区域最大宽度,默认 0,表示不限制
     * @return FME_OK：成功
     */
    public native int SetParams(long[] hFaceHandler,float scale, float min_neighbors, int min_object_width, int max_object_width);

    /**SetExtraParams
     *设置附加参数
     * @param hFaceHandler        算法实例 长度long[4]
     * @param sparams	          json 格式的参数
     * @return FME_OK：成功
     */
    public native int SetExtraParams(long[] hFaceHandler, String sparams);

    /**DetectFaceRgb
     *检测 BGR 格式图片人脸
     * @param hFaceHandler      算法实例 长度long[4]
     * @param pBGRBuf	        按 BGR 顺序存储的一维字节数组，不能包含有行字节对齐空位
     * @param nW	            图像数据宽度
     * @param nH	            图像数据高度
     * @param pRect	            预分配 4*10 int，检测到的人脸区域位置最大支持 10 个人脸位置[x-y-w-h][x-y-w-h]...[x-y-w-h]排列
     *  @param MaxNumber	    要求最大人脸的个数 1-10
     * @return 大于 0：成功，其余失败
     */
    public native int DetectFaceRgb(long[] hFaceHandler, byte[] pBGRBuf,int nW,int nH,int[] pRect,int MaxNumber);

    /**ExtractFeatureBGR
     *提取 BGR 格式图像人脸特征
     * @param hFaceHandler      算法实例 长度long[4]
     * @param pfFeature         预分配 大小为 128float 存放人脸的特征值
     * @param pBGRBuf	        按 BGR 顺序存储的一维字节数组，不能包含有行字节对齐空位
     * @param nW	            图像数据宽度
     * @param nH	            图像数据高度
     * @param pnStatus	        预留
     * @param pRect	            为 2.3 检测的人脸区域坐标 null 为重新检测并用最大的区域进行识别
     * @param landmark	        人脸关键点信息 从AliveActionDetect获取 大小为166个float
     * @return FME_OK，成功提取特征，特征存于 pfFeature。
     */
    public native int ExtractFeatureBGR(long[] hFaceHandler,float[] pfFeature,byte[] pBGRBuf,int nW,int nH,int[] pnStatus,int[] pRect,float[] landmark);
    
    /**AliveStaticDetect
     *静态活体检测
     * @param hFaceHandler      算法实例 长度long[4]
     * @param name      		保存图像的文件名
     * @param pBGRBuf	        按 BGR 顺序存储的一维字节数组，不能包含有行字节对齐空位
     * @param nW	            图像数据宽度
     * @param nH	            图像数据高度
     * @param pRect	            为 2.3 检测的人脸区域坐标 不能用null
     * @return 0-1   		    越大越接近真人 默认0.5
     */
    public native int GetImg(long[] hFaceHandler,String name,byte[] pBGRBuf,int nW,int nH, int[] pRect,int vsflag);    
	
	/**AliveStaticDetect
     *静态活体检测
     * @param hFaceHandler      算法实例 长度long[4]
     * @param pBGRBuf	        按 BGR 顺序存储的一维字节数组，不能包含有行字节对齐空位
     * @param nW	            图像数据宽度
     * @param nH	            图像数据高度
     * @param pRect	            为 2.3 检测的人脸区域坐标 不能用null
     * @return 0-1   		    越大越接近真人 默认0.8
     */
    public native float AliveStaticDetect(long[] hFaceHandler,byte[] pBGRBuf,int nW,int nH, int[] pRect);
    
    /**AliveActionDetect
     *动态活体检测
     * @param hFaceHandler      算法实例 长度long[4]
     * @param pBGRBuf	        按 BGR 顺序存储的一维字节数组，不能包含有行字节对齐空位
     * @param nW	            图像数据宽度
     * @param nH	            图像数据高度
     * @param pRect	            为 2.3 检测的人脸区域坐标 不能用null
     * @param mouth_o	        0 嘴巴闭合状态, 1 嘴巴张开状态
     * @param head_h	        0 头部姿态水平居中 1 头部姿态水平左 2头部姿态水平右  
     * @param head_v	        0 头部姿态垂直居中 1 头部姿态垂直上 2头部姿态垂直下  
     * @param blurness	        预分配 2 int 最大人脸区域的清晰度,<80 为高清晰度
     * @param landmark	        人脸关键点信息 166个float166个float
     * @param pRect_roi	        人脸的精确坐标用于显示
     * @return FME_OK
     */
    public native int AliveActionDetect(long[] hFaceHandler,byte[] pBGRBuf,int nW,int nH, int[] pRect,int[] mouth_o,float[] head_h,float[] head_v,float[] blurness,float[] landmark, int[] pRect_roi);
    
    /**Match
     *人脸特征比对(计算 2 个特征的相似度)
     * @param hFaceHandler      算法实例 长度long[4]
     * @param pfFeature1        第一个特征数组
     * @param pfFeature2	    第二个特征数组
     * @param featureLen	    特征数组长度，现在固定为 128
     * @param IdFace	    	0为生活照 1为证件照
     * @return 存放相似度，范围为 0-100
     */
    public native int Match(long[] hFaceHandler,float[] pfFeature1,float[] pfFeature2,int  featureLen,int IdFace);

    /**MatchN
     *人脸特征比对(1:N)
     * @param hFaceHandler      算法实例 长度long[4]
     * @param pfFeature         待识别的一个特征
     * @param pfFeatureLib	    特征库
     * @param libCount	        pfFeatureLib 中包含的特征个数
     * @param outIndex	        返回匹配的索引
     * @param outScore	        返回匹配的索引对应的相似度
     * @param outCount	        要返回前几个相似度最高的索引
	 * @param IdFace	    	0为生活照 1为证件照
     * @return 存放相似度，范围为 0-100
     */
    public native int MatchN(long[] hFaceHandler,float[] pfFeature,float[] pfFeatureLib,int libCount,int[] outIndex,int[] outScore,int outCount,int IdFace);
	
    /**HatInit
     *安全帽检测初始化
     * @param hFaceHandler      算法实例 长度long[4]
     * @param strModelDir      	模型的绝对路径
     * FME_OK：成功
     */
    public native int HatInit(long[] hFaceHandler,String strModelDir);

    /**DetectHat
     * 安全帽检测
     * @param hFaceHandler      算法实例 长度long[4]
     * @param pBGRBuf	        按 BGR 顺序存储的一维字节数组，不能包含有行字节对齐空位
     * @param nW	            图像数据宽度
     * @param nH	            图像数据高度
     * @param pRect	            为 2.3 检测的人脸区域坐标 不能用null
     * @return 1有安全帽 0 无安全帽 -1 不确定
     */
    public native int DetectHat(long[] hFaceHandler,byte[] pBGRBuf,int nW,int nH, int[] pRect);
		
	/**DestroyIns
     *释放安全帽检测环境
     * @param hFaceHandler    算法实例 长度long[4]
     * @return FME_OK：成功
     */
    public native int DestroyHat(long[] hFaceHandler);
}