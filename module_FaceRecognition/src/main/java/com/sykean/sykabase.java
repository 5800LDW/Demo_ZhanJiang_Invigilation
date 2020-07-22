package com.sykean;
public class sykabase{
	static {
		System.loadLibrary("sykabase");
	}
	/********以下为思源科安自用数据图像类型转换函数********/
	/**ConverArgbToRgb
	 *将ARGB8888 的位图数据转换成RGB24和灰度数据
	 * @param bmp		RGB8888 的位图对象
	 * @param rgb		RGB888 的图像位图裸数据
	 * @param gray		灰度图像的裸数据
	 * @param w			位图的w
	 * @param h			位图的h
	 * @param scan		是否缩放 default 1
	 * @return
	 */
	public native int ConverArgbToRgb(Object bmp,byte[] rgb,byte[] gray,int w,int h,int scan);

	/**ConverYuvToRgb
	 *将YUV420P的图像转换成RGB8888的位图并且旋转
	 * @param bmp		RGB8888 的位图对象
	 * @param argb		RGB8888 的位图裸数据
	 * @param yuv420	YUV420P的图像数据
	 * @param w			图像的w
	 * @param h			图像的h
	 * @param degree	图像旋转角度支持 0，90，180，270
     * @param hflip     1 左右镜像 0不做镜像处理
	 * @return
	 */
	public native int ConverYuvToRgb(Object bmp,byte[] argb,byte[] yuv420,int w,int h,int degree,int hflip);

	/**ConverYuvGrayToRgb
	 *将YUV420P的图像转换成RGB8888的位图并且旋转
	 * @param bmp		RGB8888 的位图对象
	 * @param argb		RGB8888 的位图裸数据
	 * @param yuv420	YUV420P的图像数据
	 * @param w			图像的w
	 * @param h			图像的h
	 * @param degree	图像旋转角度支持 0，90，180，270
	 * @param hflip     1 左右镜像 0不做镜像处理
	 * @return
	 */
	public native int ConverYuvGrayToRgb(Object bmp,byte[] argb,byte[] yuv420,int w,int h,int degree,int hflip);
	
	/**ConverYuv422ToARgb
	 *将YUV420P的图像转换成RGB8888的位图
	 * @param bmp		RGB8888 的位图对象
	 * @param yuv420	YUV422的图像数据
	 * @param w			图像的w
	 * @param h			图像的h
	 * @param degree	图像旋转角度支持 0，90，180，270
     * @param hflip     1 左右镜像 0不做镜像处理
	 * @return
	 */
	public native int ConverYuv422ToARgb(Object bmp,byte[] argb,byte[] yuv422,int w,int h,int degree,int hflip);
	/**CopyToImage
	 *将RGB8888的的位图裸数据拷贝到位图
	 * @param bmp		RGB8888 的位图对象
	 * @param argb		RGB8888 的位图裸数据
	 * @param w			图像的w
	 * @param h			图像的h
	 * @return
	 */
	public native int CopyToImage(Object bmp,byte[] argb,int w,int h);

	/**ImageDrawCircle
	 *在RGB8888的的位图上以中心为圆心画圆
	 * @param bmp		RGB8888 的位图对象
	 * @param w			图像的w
	 * @param h			图像的h
	 * @param range		圆的半径
	 * @return
	 */
	public native int ImageDrawCircle(Object bmp,int w,int h,int range);
    
	/**ImageDrawCircleRed
	 *在RGB8888的的位图上以中心为圆心画圆
	 * @param bmp		RGB8888 的位图对象
	 * @param w			图像的w
	 * @param h			图像的h
	 * @param range		圆的半径
	 * @return
	 */
	public native int ImageDrawCircleRed(Object bmp,int w,int h,int range);

	/**FloatToChar
	 *把浮点数据转换为字节数组
	 * @param in		浮点数组 float[128]
	 * @param out		字节数组 byte[128*4]
	 * @return
	 */
	public native int FloatToChar(float[] in,byte[] out);

	/**CharToFloat
	 *把字节数组转换为浮点数组
	 * @param in		字节数组 byte[128*4]
	 * @param out		浮点数组 float[128]
	 * @return
	 */
	public native int CharToFloat(byte[] in,float[] out);

	/***ToBmp8
	 * 保存为8位位图
	 * @param name		图像的绝对路径+名称
	 * @param gray		灰度图像
	 * @param w			图像的w
	 * @param h			图像的h
	 * @return
	 */
    public native int ToBmp8(byte[] name,byte[] gray,int w,int h);

	/***ToBmp24
	 * 保存为24位位图
	 * @param name		图像的绝对路径+名称
	 * @param rgb		RGB888图像
	 * @param w			图像的w
	 * @param h			图像的h
	 * @return
	 */
    public native int ToBmp24(byte[] name,byte[] rgb,int w,int h);

	/***ToBmp32
	 * 保存为32位位图
	 * @param name		图像的绝对路径+名称
	 * @param argb		RGB8888图像
	 * @param w			图像的w
	 * @param h			图像的h
	 * @return
	 */
    public native int ToBmp32(byte[] name,int[] argb,int w,int h);
}