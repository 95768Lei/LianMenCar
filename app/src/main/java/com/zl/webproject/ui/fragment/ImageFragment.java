package com.zl.webproject.ui.fragment;


import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.foamtrace.photopicker.SelectModel;
import com.foamtrace.photopicker.intent.PhotoPickerIntent;
import com.yanzhenjie.permission.Permission;
import com.yanzhenjie.permission.PermissionListener;
import com.zl.webproject.R;
import com.zl.webproject.base.BaseFragment;
import com.zl.webproject.base.UniversalAdapter;
import com.zl.webproject.base.UniversalViewHolder;
import com.zl.webproject.utils.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.foamtrace.photopicker.PhotoPickerActivity.EXTRA_RESULT;
import static com.zl.webproject.ui.dialog.PhotoDialog.REQUEST_CAMERA_CODE;

/**
 * @author zhanglei
 * @date 18/2/25
 */
public class ImageFragment extends BaseFragment {

    @BindView(R.id.iv_title_back)
    ImageView ivTitleBack;
    @BindView(R.id.tv_title_name)
    TextView tvTitleName;
    @BindView(R.id.iv_title_share)
    ImageView ivTitleShare;
    @BindView(R.id.tv_title_right)
    TextView tvTitleRight;
    @BindView(R.id.image_grid)
    GridView imageGrid;
    Unbinder unbinder;

    private List<String> mList = new ArrayList<>();
    private ArrayList<String> photoList = new ArrayList<>();
    private UniversalAdapter<String> adapter;

    public ImageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_image, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        initData();
        initListener();
        view.setClickable(true);
        return view;
    }

    private void initData() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == mActivity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CAMERA_CODE:
                    ArrayList<String> paths = data.getStringArrayListExtra(EXTRA_RESULT);
                    updateData(paths);
                    break;
            }
        }
    }

    private void updateData(ArrayList<String> paths) {
        photoList = paths;
        mList.clear();
        mList.addAll(paths);
        adapter.notifyDataSetChanged();
    }

    private void initListener() {
        imageGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (onImageFragmentListener != null) {
                    onImageFragmentListener.onHomeImage(mList.get(i));
                }
            }
        });
    }

    private void initView() {
        adapter = new UniversalAdapter<String>(mActivity, mList, R.layout.image_grid_item) {
            @Override
            public void convert(UniversalViewHolder holder, final int position, String s) {
                ImageView image = holder.getView(R.id.image_item);
                ImageLoader.loadImageFile(mActivity, s, image);
                holder.getView(R.id.iv_clear_item).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mList.remove(position);
                        photoList.remove(position);
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        };
        imageGrid.setAdapter(adapter);
        tvTitleName.setText("选择图片");
        tvTitleRight.setText("保存");
        tvTitleRight.setVisibility(View.VISIBLE);
    }

    /**
     * 打开相册的方法
     */
    private void openAlbum() {

        if (!isPermission(Manifest.permission.READ_EXTERNAL_STORAGE)) {
            applyPermission(Permission.STORAGE, new PermissionListener() {
                @Override
                public void onSucceed(int requestCode, @NonNull List<String> grantPermissions) {
                    PhotoPickerIntent intent = new PhotoPickerIntent(mActivity);
                    intent.setSelectModel(SelectModel.MULTI);
                    intent.setShowCarema(false); // 是否显示拍照， 默认false
                    intent.setMaxTotal(9); // 最多选择照片数量，默认为9
                    intent.setSelectedPaths(photoList); // 已选中的照片地址， 用于回显选中状态
                    mActivity.startActivityForResult(intent, REQUEST_CAMERA_CODE);
                }

                @Override
                public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {
                    showToast("取消了授权，无法打开相册");
                }
            });
            return;
        }

        PhotoPickerIntent intent = new PhotoPickerIntent(mActivity);
        intent.setSelectModel(SelectModel.MULTI);
        intent.setShowCarema(false); // 是否显示拍照， 默认false
        intent.setMaxTotal(9); // 最多选择照片数量，默认为9
        intent.setSelectedPaths(photoList); // 已选中的照片地址， 用于回显选中状态
        mActivity.startActivityForResult(intent, REQUEST_CAMERA_CODE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.iv_title_back, R.id.tab_add_image, R.id.tv_title_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_title_back:
                back();
                break;
            case R.id.tv_title_right:
                commit();
                break;
            case R.id.tab_add_image:
                openAlbum();
                break;
        }
    }

    private void commit() {
        if (onImageFragmentListener != null) {
            onImageFragmentListener.onHide();
            onImageFragmentListener.onImageList(mList);
        }
    }

    private void back() {
        if (onImageFragmentListener != null) {
            onImageFragmentListener.onHide();
        }
    }

    private OnImageFragmentListener onImageFragmentListener;

    public void setOnImageFragmentListener(OnImageFragmentListener onImageFragmentListener) {
        this.onImageFragmentListener = onImageFragmentListener;
    }

    public interface OnImageFragmentListener {
        void onHide();

        void onHomeImage(String path);

        void onImageList(List<String> path);
    }

}
