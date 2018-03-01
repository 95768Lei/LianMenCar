package com.zl.webproject.ui.dialog;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.foamtrace.photopicker.SelectModel;
import com.foamtrace.photopicker.intent.PhotoPickerIntent;
import com.zl.webproject.R;
import com.zl.webproject.base.BaseDialog;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/11/24.
 */

public class PhotoDialog extends BaseDialog implements View.OnClickListener {
    public static final int REQUEST_CAMERA_CODE = 741;
    private TextView tvCamera;
    private TextView tvPhoto;
    private TextView tvCancel;
    //相册
    public static final int SELECT_PHOTO = 0x123;
    //相机
    public static final int PICK_FROM_CAMERA = 0x234;
    public String imagePath;
    private ArrayList<String> photoList = new ArrayList<>();

    public PhotoDialog(Activity mActivity) {
        super(mActivity);
        initView();
        initListener();
    }


    private void initListener() {
        tvCamera.setOnClickListener(this);
        tvPhoto.setOnClickListener(this);
        tvCancel.setOnClickListener(this);
    }

    private void initView() {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.choose_photo_layout, null);
        tvCamera = view.findViewById(R.id.tv_camera);
        tvPhoto = view.findViewById(R.id.tv_photo);
        tvCancel = view.findViewById(R.id.tv_cancel);
        initPopupWindow(view);
    }

    public String getImagePath() {
        return imagePath;
    }

    @Override
    public void showDialog(View view) {
        super.showDialog(view);

    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.tv_camera) {
            if (onChoosePhotoListener != null) {
                onChoosePhotoListener.openCamera();
            }
        } else if (i == R.id.tv_photo) {
            if (onChoosePhotoListener != null) {
                onChoosePhotoListener.openAlbum();
            }
        }
        dismissDialog();
    }

    /**
     * 进行拍照
     */
    public void openCamera() {

        imagePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Ybjk";
        File file = new File(imagePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        long time = System.currentTimeMillis();
        String imageName = new SimpleDateFormat("yyMMddHHmmss").format(new Date(time));
        imagePath = imagePath + "/" + imageName + ".jpg";
        File imageFile = new File(imagePath);
        Uri uri = Uri.fromFile(imageFile);

        //拍照
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        //        intent.putExtra("android.intent.extras.CAMERA_FACING", 1);
        mActivity.startActivityForResult(intent, PICK_FROM_CAMERA);
    }

    /**
     * 打开相册的方法(多选)
     */
    public void openAlbum() {
        PhotoPickerIntent intent = new PhotoPickerIntent(mActivity);
        intent.setSelectModel(SelectModel.MULTI);
        intent.setShowCarema(false); // 是否显示拍照， 默认false
        intent.setMaxTotal(9); // 最多选择照片数量，默认为9
        intent.setSelectedPaths(photoList); // 已选中的照片地址， 用于回显选中状态
        mActivity.startActivityForResult(intent, REQUEST_CAMERA_CODE);
    }

    /**
     * 打开相册的方法(单选)
     */
    public void openSingleAlbum() {
        PhotoPickerIntent intent = new PhotoPickerIntent(mActivity);
        intent.setSelectModel(SelectModel.SINGLE);
        intent.setShowCarema(false); // 是否显示拍照， 默认false
        intent.setSelectedPaths(photoList); // 已选中的照片地址， 用于回显选中状态
        mActivity.startActivityForResult(intent, REQUEST_CAMERA_CODE);
    }

    public void setPhotoList(ArrayList<String> list) {
        photoList.clear();
        photoList.addAll(list);
    }

    private OnChoosePhotoListener onChoosePhotoListener;

    public void setOnChoosePhotoListener(OnChoosePhotoListener onChoosePhotoListener) {
        this.onChoosePhotoListener = onChoosePhotoListener;
    }

    public interface OnChoosePhotoListener {
        void openAlbum();

        void openCamera();
    }

}
