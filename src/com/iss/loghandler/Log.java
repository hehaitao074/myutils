
package com.iss.loghandler;

/**
 * 一个log-output包装。有用的日志输出在运行前关闭 当一个错误出现开发者仍然可以收集应用调试信息
 * 
 * @author perry.li
 */
public class Log {

    /**
     * 是否显示应用的log 在应用上线的时候需要修改此状态表示为 false
     */
    public static boolean IS_SHOW_LOG = true;

    public interface LogInterface {

        void d(String logTag, String logText);

        void e(String logTag, String logText);

        void w(String logTag, String logText);

        void v(String logTag, String logText);

        void i(String logTag, String logText);

        void print(String logText);

    }

    private static LogInterface instance;

    public static LogInterface getInstance() {
        if (instance == null)
            instance = newDefaultAndroidLog();
        return instance;
    }

    public static void setInstance(LogInterface instance) {
        Log.instance = instance;
    }

    private static LogInterface newDefaultAndroidLog() {
        return new LogInterface() {

            @Override
            public void w(String logTag, String logText) {
                android.util.Log.w(logTag, logText);
            }

            @Override
            public void v(String logTag, String logText) {
                android.util.Log.v(logTag, logText);
            }

            @Override
            public void i(String logTag, String logText) {
                android.util.Log.i(logTag, logText);
            }

            @Override
            public void e(String logTag, String logText) {
                android.util.Log.e(logTag, logText);
            }

            @Override
            public void d(String logTag, String logText) {
                android.util.Log.d(logTag, logText);
            }

            @Override
            public void print(String logText) {
                android.util.Log.d("Debug Output", logText);
            }
        };
    }

    public static void d(String logTag, String logText) {
        if (IS_SHOW_LOG) {
            getInstance().d(logTag, logText);
        }
    }

    public static void e(String logTag, String logText) {
        if (IS_SHOW_LOG) {
            getInstance().e(logTag, logText);
        }

    }

    public static void w(String logTag, String logText) {
        if (IS_SHOW_LOG) {
            getInstance().w(logTag, logText);
        }

    }

    public static void v(String logTag, String logText) {
        if (IS_SHOW_LOG) {
            getInstance().v(logTag, logText);
        }

    }

    public static void i(String logTag, String logText) {
        if (IS_SHOW_LOG) {
            getInstance().i(logTag, logText);
        }

    }

    public static void out(String logText) {
        if (IS_SHOW_LOG) {
            getInstance().print(logText);
        }
    }

    /**
     * @param matrixName
     * @param a 必须是一个 4x4 matrix 矩阵
     * @return the debug string
     */
    public static String floatMatrixToString(String matrixName, float[] a) {
        String s = "";
        s += "Matrix: " + matrixName + "\n";
        s += "\t " + a[0] + "," + a[1] + "," + a[2] + "," + a[3] + " \n";
        s += "\t " + a[4] + "," + a[5] + "," + a[6] + "," + a[7] + " \n";
        s += "\t " + a[8] + "," + a[9] + "," + a[10] + "," + a[11] + " \n";
        s += "\t " + a[12] + "," + a[13] + "," + a[14] + "," + a[15] + " \n";
        return s;
    }

}
