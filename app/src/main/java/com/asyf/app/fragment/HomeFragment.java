package com.asyf.app.fragment;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.asyf.app.R;
import com.asyf.app.activity.ListActivity;
import com.asyf.app.activity.LoginTestActivity;
import com.asyf.app.activity.VideoPlayerActivity;
import com.asyf.app.common.Logger;
import com.asyf.app.jni.JniTest;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private final String TAG = "HomeFragment";
    private String content = "12ss";

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    public HomeFragment() {

    }

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_home, container, false);
        //TextView txt_content = (TextView) view.findViewById(R.id.txt_content);
        //txt_content.setText(content);
        verifyStoragePermissions(getActivity());
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final FragmentActivity activity = getActivity();
        Button button = activity.findViewById(R.id.swipe_refresh);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logger.e(TAG, "点击碎片中的按钮");
                Intent intent = new Intent(activity, ListActivity.class);
                startActivity(intent);
            }
        });
        Button test = activity.findViewById(R.id.test);
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, LoginTestActivity.class);
                startActivity(intent);
            }
        });
        Button jtc = activity.findViewById(R.id.javatoc);
        jtc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String result = new JniTest().getStrFromC();
                Toast.makeText(activity, result, Toast.LENGTH_SHORT).show();
            }
        });
        Button jiecao = activity.findViewById(R.id.jiaozi);
        jiecao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, VideoPlayerActivity.class);
                startActivity(intent);
            }
        });
        Button checkVersion = activity.findViewById(R.id.check_version);
        checkVersion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new QMUIDialog.MessageDialogBuilder(activity).setTitle("title").setMessage("确认要更新吗？")
                        .addAction("暂不更新", new QMUIDialogAction.ActionListener() {
                            @Override
                            public void onClick(QMUIDialog dialog, int index) {
                                dialog.dismiss();
                            }
                        }).addAction("立即更新", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        Toast.makeText(activity, "点击了更新", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        //下载文件
                        //downloadNewVersion(activity);
                        downLoad2(activity);
                    }
                }).show();
            }
        });
        Button file = activity.findViewById(R.id.file);
        file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String path = Environment.getExternalStorageDirectory() + "/VersionChecker/";
                    File file = new File(path + "test22.txt");
                    // 目录不存在创建目录
                    if (!file.getParentFile().exists())
                        file.getParentFile().mkdirs();
                    Logger.e(TAG, path);
                    FileOutputStream fos = new FileOutputStream(file);
                    String data = "djsddjkji22i222中文";
                    fos.write(data.getBytes());
                    fos.close();
                    //FileOutputStream out = activity.openFileOutput("test2.txt", Context.MODE_PRIVATE);
                    // BufferedWriter w = new BufferedWriter(new OutputStreamWriter(out));
                    //w.write("测试数据");
                    //w.close();
                    //out.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void downLoad2(FragmentActivity activity) {

        final ProgressDialog pd; // 进度条对话框
        pd = new ProgressDialog(activity);
        pd.setCancelable(false);// 必须一直下载完，不可取消
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.setMessage("正在下载安装包，请稍后");
        pd.setTitle("版本升级");
        pd.show();
        new Thread() {
            @Override
            public void run() {
                try {
                    String path = "http://192.168.0.130:8080/mtest/testDownload";
                    URL url = new URL(path);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Accept-Encoding", "identity");
                    conn.setConnectTimeout(5000);
                    // 获取到文件的大小
                    pd.setMax(conn.getContentLength());
                    InputStream is = conn.getInputStream();
                    String fileName = Environment.getExternalStorageDirectory() + "/apptest.apk";
                    File file = new File(fileName);
                    // 目录不存在创建目录
                    if (!file.getParentFile().exists())
                        file.getParentFile().mkdirs();
                    FileOutputStream fos = new FileOutputStream(file);
                    BufferedInputStream bis = new BufferedInputStream(is);
                    byte[] buffer = new byte[1024];
                    int len;
                    int total = 0;
                    while ((len = bis.read(buffer)) != -1) {
                        fos.write(buffer, 0, len);
                        total += len;
                        // 获取当前下载量
                        pd.setProgress(total);
                    }
                    fos.close();
                    bis.close();
                    is.close();
                    sleep(3000);
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
                    getActivity().startActivity(intent);
                    // 结束掉进度条对话框
                    pd.dismiss();
                } catch (Exception e) {
                    pd.dismiss();
                }
            }
        }.start();
    }

    private void downloadNewVersion(FragmentActivity activity) {

        String url = "http://192.168.0.130:8080/mtest/testDownload";
        RequestParams params = new RequestParams(url);
        //自定义保存路径，Environment.getExternalStorageDirectory()：SD卡的根目录
        String path = Environment.getExternalStorageDirectory() + "/apptest.apk";
        //目录不存在则创建目录
        /*File file = new File(path);
        if (!file.exists())
            file.getParentFile().mkdirs();*/
        params.setSaveFilePath(path);

        Logger.e(TAG, "路径:" + path);
        //自动为文件命名
        params.setAutoRename(true);
        params.setUseCookie(false);
        params.setLoadingUpdateMaxTimeSpan(10);

        final ProgressDialog pd = new ProgressDialog(activity);
        pd.setCancelable(false);//不能取消，一直等待下载完成
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.setMessage("正在下载安装包，请稍后");
        pd.setTitle("版本升级");
        pd.show();
        x.http().post(params, new Callback.ProgressCallback<File>() {
            @Override
            public void onSuccess(File result) {
                pd.dismiss();
                //apk下载完成后，调用系统的安装方法
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(result), "application/vnd.android.package-archive");
                getActivity().startActivity(intent);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
            }

            @Override
            public void onCancelled(CancelledException cex) {
            }

            @Override
            public void onFinished() {
            }

            //网络请求之前回调
            @Override
            public void onWaiting() {
            }

            //网络请求开始的时候回调
            @Override
            public void onStarted() {
            }

            //下载的时候不断回调的方法
            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
                //当前进度和文件总大小
                Logger.i("JAVA", "current：" + current + "，total：" + total);
                pd.setMax((int) total);
                pd.setProgress((int) current);
            }
        });
    }
}
