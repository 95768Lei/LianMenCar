package com.zl.webproject.ui.dialog;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zl.webproject.R;
import com.zl.webproject.base.RecyclerAdapter;
import com.zl.webproject.base.UniversalAdapter;
import com.zl.webproject.base.UniversalViewHolder;
import com.zl.webproject.base.ViewHolder;
import com.zl.webproject.model.CarDictionaryEntity;
import com.zl.webproject.model.MoreTagBean;
import com.zl.webproject.model.TagBean;
import com.zl.webproject.utils.API;
import com.zl.webproject.utils.HttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import okhttp3.Request;

/**
 * Created by Administrator on 2018/2/23.
 */

public class MoreTagDialog {
    private Activity mActivity;
    private ListView listView;
    private List<MoreTagBean> mList = new ArrayList<>();
    private UniversalAdapter<MoreTagBean> mAdapter;
    private PopupWindow mPopupWindow;
    private Map<Integer, Integer> sparseArray = new HashMap<>();
    private View bgTv;

    public MoreTagDialog(Activity mActivity) {
        this.mActivity = mActivity;
        initView();
        initData();
        initListener();
    }

    private void initListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mAdapter.notifyDataSetChanged();

            }
        });

        bgTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    private void initData() {
        getTagList();
    }

    private void getTagList() {
        Map<String, String> params = new HashMap<>();
        params.put("data", "");
        HttpUtils.getInstance().Post(mActivity, params, API.getRetrieval, new HttpUtils.OnOkHttpCallback() {
            @Override
            public void onSuccess(String body) {
                try {

                    JSONArray array = new JSONArray(body);
                    MoreTagBean displacement = new MoreTagBean();
                    displacement.setTitle("排量");
                    //排量
                    JSONObject object = array.optJSONObject(9);
                    JSONArray displacementType = object.optJSONArray("value");
                    List<TagBean> displacementTypeList = new ArrayList<TagBean>();
                    for (int i = 0; i < displacementType.length(); i++) {
                        CarDictionaryEntity entity = new Gson().fromJson(displacementType.opt(i).toString(), CarDictionaryEntity.class);
                        TagBean bean = new TagBean();
                        if (i == 0) {
                            bean.setTag(entity.getDictName() + "L以下");
                            bean.setStart("0");
                            bean.setEnd(entity.getDictName());
                        } else if (i == (displacementType.length() - 1)) {
                            bean.setTag(entity.getDictName() + "L以上");
                            bean.setStart(entity.getDictName());
                            bean.setEnd("" + Integer.MAX_VALUE);
                        } else if (entity.getDictName().contains("-")) {
                            String[] split = entity.getDictName().split("-");
                            bean.setTag(split[0] + "L - " + split[1] + "L");
                            bean.setStart(split[0]);
                            bean.setEnd(split[1]);
                        } else {
                            bean.setTag(entity.getDictName());
                            bean.setStart("0");
                            bean.setEnd("" + Integer.MAX_VALUE);
                        }
                        displacementTypeList.add(bean);
                    }
                    displacement.setTagList(displacementTypeList);

                    //燃油类型
                    MoreTagBean fue = new MoreTagBean();
                    fue.setTitle("燃油类型");
                    JSONObject object2 = array.optJSONObject(6);
                    JSONArray fuelType = object2.optJSONArray("value");
                    List<TagBean> fuelList = new ArrayList<TagBean>();
                    for (int i = 0; i < fuelType.length(); i++) {
                        CarDictionaryEntity entity = new Gson().fromJson(fuelType.opt(i).toString(), CarDictionaryEntity.class);
                        TagBean bean = new TagBean();
                        bean.setTag(entity.getDictName());
                        bean.setId(entity.getId() + "");
                        fuelList.add(bean);
                    }
                    fue.setTagList(fuelList);

                    //车辆级别
                    MoreTagBean carlv = new MoreTagBean();
                    carlv.setTitle("车辆级别");
                    JSONObject object3 = array.optJSONObject(1);
                    JSONArray carlvType = object3.optJSONArray("value");
                    List<TagBean> carlvList = new ArrayList<TagBean>();
                    for (int i = 0; i < carlvType.length(); i++) {
                        CarDictionaryEntity entity = new Gson().fromJson(carlvType.opt(i).toString(), CarDictionaryEntity.class);
                        TagBean bean = new TagBean();
                        bean.setTag(entity.getDictName());
                        bean.setId(entity.getId() + "");
                        carlvList.add(bean);
                    }
                    carlv.setTagList(carlvList);

                    //变速方式
                    MoreTagBean gearbox = new MoreTagBean();
                    gearbox.setTitle("车辆级别");
                    JSONObject object4 = array.optJSONObject(0);
                    JSONArray gearboxType = object4.optJSONArray("value");
                    List<TagBean> gearboxList = new ArrayList<TagBean>();
                    for (int i = 0; i < gearboxType.length(); i++) {
                        CarDictionaryEntity entity = new Gson().fromJson(gearboxType.opt(i).toString(), CarDictionaryEntity.class);
                        TagBean bean = new TagBean();
                        bean.setTag(entity.getDictName());
                        bean.setId(entity.getId() + "");
                        gearboxList.add(bean);
                    }
                    gearbox.setTagList(gearboxList);

                    //可不可过户数据
                    MoreTagBean label = new MoreTagBean();
                    label.setTitle("车辆标签");
                    JSONObject object5 = array.optJSONObject(2);
                    JSONArray huType = object5.optJSONArray("value");
                    List<TagBean> noList = new ArrayList<TagBean>();
                    List<TagBean> okList = new ArrayList<TagBean>();
                    JSONObject okJson = huType.optJSONObject(0);
                    JSONObject noJson = huType.optJSONObject(1);
                    JSONArray okArray = okJson.optJSONArray("dictionaryList");
                    JSONArray noArray = noJson.optJSONArray("dictionaryList");
                    for (int i = 0; i < okArray.length(); i++) {
                        CarDictionaryEntity entity = new Gson().fromJson(okArray.opt(i).toString(), CarDictionaryEntity.class);
                        TagBean bean = new TagBean();
                        bean.setTag(entity.getDictName());
                        bean.setId(entity.getId() + "");
                        okList.add(bean);
                    }
                    for (int i = 0; i < noArray.length(); i++) {
                        CarDictionaryEntity entity = new Gson().fromJson(noArray.opt(i).toString(), CarDictionaryEntity.class);
                        TagBean bean = new TagBean();
                        bean.setTag(entity.getDictName());
                        bean.setId(entity.getId() + "");
                        noList.add(bean);
                    }
                    okList.addAll(noList);
                    label.setTagList(okList);

                    mList.add(displacement);
                    mList.add(fue);
                    mList.add(carlv);
                    mList.add(gearbox);
                    mList.add(label);

                    mAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Request error, Exception e) {

            }
        });
    }

    private void initView() {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.listview_layout, null);
        listView = view.findViewById(R.id.list_view);
        bgTv = view.findViewById(R.id.bg_tv);
        mAdapter = new UniversalAdapter<MoreTagBean>(mActivity, mList, R.layout.more_list_item) {
            @Override
            public void convert(UniversalViewHolder holder, final int position, MoreTagBean s) {
                holder.setText(R.id.tv_car_tag, s.getTitle());
                RecyclerView itemRv = holder.getView(R.id.list_item_rv);
                itemRv.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
                final RecyclerAdapter<TagBean> adapter = new RecyclerAdapter<TagBean>(mActivity, s.getTagList(), R.layout.more_tv_item) {
                    @Override
                    protected void convert(ViewHolder holder, TagBean s, int p) {
                        TextView view = holder.getView(R.id.tv_more_item);
                        view.setText(s.getTag());
                        view.setSelected(false);
                        try {
                            Integer integer = sparseArray.get(position);
                            if (integer == p) {
                                view.setSelected(true);
                            }
                        } catch (Exception e) {

                        }
                    }
                };
                itemRv.setAdapter(adapter);
                adapter.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int i) {
                        try {
                            Integer integer = sparseArray.get(position);
                            if (integer == i) {
                                sparseArray.remove(position);
                            } else {
                                sparseArray.put(position, i);
                            }
                        } catch (Exception e) {
                            sparseArray.put(position, i);
                        }
                        adapter.notifyDataSetChanged();
                        handleData();
                    }
                });
            }
        };
        listView.setAdapter(mAdapter);
        mPopupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mPopupWindow.setAnimationStyle(R.style.WindowScaleAnim);

    }

    /**
     * 处理数据
     */
    private void handleData() {
        if (onReturnMapDataListener != null) {
            Map<String, String> params = new HashMap<String, String>();
            params.put("lv", "");
            params.put("fuel", "");
            params.put("label", "");
            params.put("gearbox", "");
            params.put("displacementStart", "");
            params.put("displacementEnd", "");
            Set<Map.Entry<Integer, Integer>> entries = sparseArray.entrySet();
            for (Map.Entry<Integer, Integer> entry : entries) {
                switch (entry.getKey()) {
                    case 0:
                        TagBean bean = mList.get(entry.getKey()).getTagList().get(entry.getValue());
                        params.put("displacementStart", bean.getStart());
                        params.put("displacementEnd", bean.getEnd());
                        break;
                    case 1:
                        params.put("fuel", mList.get(entry.getKey()).getTagList().get(entry.getValue()).getId());
                        break;
                    case 2:
                        params.put("lv", mList.get(entry.getKey()).getTagList().get(entry.getValue()).getId());
                        break;
                    case 3:
                        params.put("gearbox", mList.get(entry.getKey()).getTagList().get(entry.getValue()).getId());
                        break;
                    case 4:
                        params.put("label", mList.get(entry.getKey()).getTagList().get(entry.getValue()).getId());
                        break;
                }

            }
            onReturnMapDataListener.returnData(params);
        }
    }

    public void showDialog(View view) {
        mPopupWindow.showAsDropDown(view);
    }

    public void dismiss() {
        mPopupWindow.dismiss();
    }

    public boolean isShow() {
        return mPopupWindow.isShowing();
    }

    private OnReturnMapDataListener onReturnMapDataListener;

    public void setOnReturnMapDataListener(OnReturnMapDataListener onReturnMapDataListener) {
        this.onReturnMapDataListener = onReturnMapDataListener;
    }

    public interface OnReturnMapDataListener {
        void returnData(Map<String, String> data);
    }
}
