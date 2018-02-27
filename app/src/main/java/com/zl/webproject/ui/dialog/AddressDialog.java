package com.zl.webproject.ui.dialog;

import android.app.Activity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zl.webproject.R;
import com.zl.webproject.base.BaseDialog;
import com.zl.webproject.model.CityBean;
import com.zl.webproject.pinyin.CharacterParser;
import com.zl.webproject.pinyin.PinyinComparator;
import com.zl.webproject.ui.CityAdapter;
import com.zl.webproject.utils.API;
import com.zl.webproject.utils.HttpUtils;
import com.zl.webproject.utils.SpUtlis;
import com.zl.webproject.view.SideBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Request;

/**
 * Created by Administrator on 2018/2/22.
 */

public class AddressDialog extends BaseDialog {

    private SideBar dialogSide;
    private TextView dialogText;
    private ListView dialogListView;
    private CharacterParser characterParser;
    private PinyinComparator pinyinComparator;
    private CityAdapter adapter;
    private List<CityBean> dataLsit = new ArrayList<>();
    private List<CityBean> sourceDataList = new ArrayList<>();
    private TextView cityTv1;
    private TextView catalog1;
    private TextView cityTv2;
    private TextView catalog2;
    private TextView cityTv3;
    private TextView catalog3;

    public AddressDialog(Activity mActivity) {
        super(mActivity);
        initView();
        initData();
        initListener();
    }

    private void initData() {
        //初始化数据
        Map<String, String> params = new HashMap<>();
        params.put("data", "");
        HttpUtils.getInstance().POST(mActivity, new JSONObject(params).toString(), API.getCity, new HttpUtils.OnOkHttpCallback() {
            @Override
            public void onSuccess(String body) {
                try {
                    JSONArray array = new JSONArray(body);
                    for (int i = 0; i < array.length(); i++) {
                        dataLsit.add(new Gson().fromJson(array.optString(i), CityBean.class));
                    }

                    //对数据进行排序
                    if (dataLsit != null && dataLsit.size() > 0) {
                        sourceDataList = filledData(dataLsit); //过滤数据为有字母的字段  现在有字母 别的数据没有
                    }

                    //还原除了带字母字段的其他数据
                    for (int i = 0; i < dataLsit.size(); i++) {
                        sourceDataList.get(i).setCityName(dataLsit.get(i).getCityName());
                    }
                    dataLsit = null; //释放资源

                    // 根据a-z进行排序源数据
                    Collections.sort(sourceDataList, pinyinComparator);
                    //初始化适配器
                    adapter = new CityAdapter(mActivity, sourceDataList, R.layout.city_item);
                    dialogListView.setAdapter(adapter);
                    //刷新适配器
                    adapter.notifyDataSetChanged();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Request error, Exception e) {

            }
        });


    }

    private void initListener() {
        dialogSide.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String s) {
                //该字母首次出现的位置
                int position = adapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    dialogListView.setSelection(position);
                }
            }
        });
        dialogListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i < 3) {
                    return;
                }
                int position = i - 3;
                CityBean cityBean = sourceDataList.get(position);
                cityTv2.setText(cityBean.getCityName());
                //储存选择的城市
                SpUtlis.setCurrentLocationData(mActivity, cityBean);
                if (onAddressDataListener != null) {
                    onAddressDataListener.addressData(cityBean);
                }
            }
        });


    }

    @Override
    public void showDialog(View view) {
        super.showDialog(view);
        setAddressData();
    }

    private void initView() {

        //实例化汉字转拼音类
        characterParser = CharacterParser.getInstance();
        pinyinComparator = PinyinComparator.getInstance();

        View view = LayoutInflater.from(mActivity).inflate(R.layout.address_layout, null);
        View header1 = LayoutInflater.from(mActivity).inflate(R.layout.city_item, null);
        View header2 = LayoutInflater.from(mActivity).inflate(R.layout.city_item, null);
        View header3 = LayoutInflater.from(mActivity).inflate(R.layout.city_item, null);
        header3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CityBean cityBean = new CityBean();
                cityBean.setCityName("全国城市");
                cityBean.setCityData("全国城市");
                cityBean.setCityCode("");
                cityTv2.setText(cityBean.getCityName());
                //储存选择的城市
                SpUtlis.setCurrentLocationData(mActivity, cityBean);
                if (onAddressDataListener != null) {
                    onAddressDataListener.addressData(cityBean);
                }
            }
        });
        view.findViewById(R.id.iv_title_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismissDialog();
            }
        });
        TextView titleName = view.findViewById(R.id.tv_title_name);
        titleName.setText("选择城市");
        cityTv1 = header1.findViewById(R.id.city_name);
        catalog1 = header1.findViewById(R.id.catalog);
        cityTv2 = header2.findViewById(R.id.city_name);
        catalog2 = header2.findViewById(R.id.catalog);
        cityTv3 = header3.findViewById(R.id.city_name);
        catalog3 = header3.findViewById(R.id.catalog);
        catalog1.setText("当前定位城市");
        catalog2.setText("当前选择城市");
        catalog3.setText("全");
        cityTv3.setText("全国城市");
        dialogSide = view.findViewById(R.id.dialog_side);
        dialogText = view.findViewById(R.id.dialog_text);
        dialogListView = view.findViewById(R.id.dialog_listView);
        dialogSide.setTextView(dialogText);
        dialogListView.addHeaderView(header1);
        dialogListView.addHeaderView(header2);
        dialogListView.addHeaderView(header3);
        initPopupWindow(view);
    }

    /**
     * 赋值当前选择的城市
     */
    private void setAddressData() {
        CityBean currentCityBean = SpUtlis.getCurrentLocationData(mActivity);
        String name = currentCityBean.getCityName();
        if (!TextUtils.isEmpty(name)) {
            cityTv2.setText(name);
        } else {
            cityTv2.setText("无");
        }

        CityBean cityBean = SpUtlis.getLocationData(mActivity);
        String cityName = cityBean.getCityName();
        if (!TextUtils.isEmpty(cityName)) {
            cityTv1.setText(cityName);
        } else {
            cityTv1.setText("无");
        }
    }

    /**
     * 为ListView填充数据
     *
     * @param
     * @return
     */
    private List<CityBean> filledData(List<CityBean> lsit) {
        List<CityBean> mCityBeanList = new ArrayList<CityBean>();

        for (int i = 0; i < lsit.size(); i++) {
            CityBean friendModel = new CityBean();
            friendModel.setCityName(lsit.get(i).getCityName());
            //汉字转换成拼音
            String pinyin = characterParser.getSelling(lsit.get(i).getCityName());
            String sortString = pinyin.substring(0, 1).toUpperCase();

            // 正则表达式，判断首字母是否是英文字母
            if (sortString.matches("[A-Z]")) {
                friendModel.setLetters(sortString.toUpperCase());
            } else {
                friendModel.setLetters("#");
            }

            mCityBeanList.add(friendModel);
        }
        return mCityBeanList;

    }

    private OnAddressDataListener onAddressDataListener;

    public void setOnAddressDataListener(OnAddressDataListener onAddressDataListener) {
        this.onAddressDataListener = onAddressDataListener;
    }

    public interface OnAddressDataListener {
        void addressData(CityBean cityBean);
    }

}
