package com.li.estatesalemanapp.ui;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.cangjie.basetool.mvp.base.PresenterActivity;
import com.li.data.http.bean.CheckFace;
import com.li.data.http.bean.Record;
import com.li.estatesalemanapp.R;
import com.li.estatesalemanapp.presenter.InputRecordPresenter;
import com.li.estatesalemanapp.presenter.view.InputRecordView;
import com.li.estatesalemanapp.util.ImageLoaderHelper;

import java.io.File;
import java.io.FileNotFoundException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.li.estatesalemanapp.R.id.iv;

public class InputRecordActivity extends PresenterActivity<InputRecordPresenter> implements InputRecordView {
    private static final String TAG = "InputRecordActivity";
    private static final int ALBUM_REQUEST_CODE = 84;
    @Bind(R.id.et_title)
    EditText et_title;
    @Bind(R.id.et_content)
    EditText et_content;
    @Bind(iv)
    ImageView iv_photo;
    @Bind(R.id.btn_submit)
    Button btn_submit;
    @Bind(R.id.ll_container)
    LinearLayout ll_container;

    private String faceToken; //根据图片路径获取
    private String realFilePath;
    private AlertDialog dialog;

    @Override
    protected InputRecordPresenter createPresenter() {
        return new InputRecordPresenter(this, getApplicationContext());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_record);
        ButterKnife.bind(this);

        int id = getIntent().getIntExtra("id", -1);
        if (id != -1)
            mPresenter.loadRecord(id);
    }

    @OnClick(iv)
    public void choosePhoto(){
        Intent pickIntent = new Intent(Intent.ACTION_PICK, null);
        // 如果限制上传到服务器的图片类型时可以直接写如："image/jpeg 、 image/png等的类型"
        pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(pickIntent, ALBUM_REQUEST_CODE);
    }

    @OnClick(R.id.btn_check)
    public void checkPhoto(){
        if (TextUtils.isEmpty(realFilePath)){
            disPlay("请选择图片!");
            return;
        }
        File file = new File(realFilePath);
        mPresenter.checkPhoto(file);
    }

    @OnClick(R.id.btn_submit)
    public void submit(){
        if (TextUtils.isEmpty(faceToken)){
            disPlay("请选择图片，并检查图片!");
            return;
        }
        String title = et_title.getText().toString().trim();
        String content = et_content.getText().toString().trim();
        mPresenter.submit(title, content, faceToken);
    }

    @Override
    public void faceTokenFailed(String message) {

    }

    @Override
    public void faceTokenSuccess(final CheckFace checkFace) {
        faceToken = checkFace.getFaceToken();
        if (checkFace.getLastRecordId() == -1)
            return;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        dialog = builder.setTitle("提示")
                .setMessage("找到类似图片中客户的信息，是否要查看？")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ll_container.setVisibility(View.VISIBLE);
                    }
                })
                .setPositiveButton("查看", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 根据ID访问后台
                        mPresenter.loadRecord(checkFace.getLastRecordId());
                    }
                }).show();
    }

    @Override
    public void submitSuccess(String returnInfo) {
        disPlay(returnInfo);
        startActivity(new Intent(this, RecordListActivity.class));
        finish();
    }

    @Override
    public void submitFailed(String returnInfo) {
        disPlay(returnInfo);
    }

    @Override
    public void showData(Record record) {
        ll_container.setVisibility(View.VISIBLE);
        et_title.setText(record.getTitle());
        et_content.setText(record.getContent());
        ImageLoaderHelper.displayImage(record.getPhotoUrl(),iv_photo);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ALBUM_REQUEST_CODE){
            Uri uri = data.getData();
            realFilePath = getRealFilePath(uri);
            Log.e("uri", uri.toString());
            Log.e("realFilePath", realFilePath.toString());
            ContentResolver cr = this.getContentResolver();
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
                /* 将Bitmap设定到ImageView */
                iv_photo.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                Log.e("Exception", e.getMessage(),e);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    public String getRealFilePath(Uri uri) {
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = mContext.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dialog != null && dialog.isShowing())
            dialog.dismiss();
    }
}
