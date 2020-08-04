package com.example.shopdemo.frag.person;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.shopdemo.R;
import com.example.shopdemo.adpter.GridPersonAdapter;
import com.example.shopdemo.adpter.MyGridAdapter;
import com.example.shopdemo.entiy.MyGirdBean;
import com.example.shopdemo.entiy.OrderGridBean;
import com.example.shopdemo.util.UseTool;
import com.example.shopdemo.view.MyGridView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class PersonFragment extends Fragment {

    @BindView(R.id.img_btn_person_setting)
    ImageView imgBtnPersonSetting;
    @BindView(R.id.person_head_img)
    ImageView personHeadImg;
    @BindView(R.id.person_head_name_login)
    TextView personHeadNameLogin;
    @BindView(R.id.person_btn_yika)
    Button personBtnYika;
    @BindView(R.id.person_btn_jiz)
    Button personBtnJiz;
    @BindView(R.id.person_lin_btn_two)
    LinearLayout personLinBtnTwo;
    @BindView(R.id.person_btn_login)
    Button personBtnLogin;
    @BindView(R.id.person_view_dialog)
    RelativeLayout personViewDialog;
    @BindView(R.id.text_num_balance)
    TextView textNumBalance;
    @BindView(R.id.text_num_vouchers)
    TextView textNumVouchers;
    @BindView(R.id.text_num_integral)
    TextView textNumIntegral;
    @BindView(R.id.text_shop_num)
    TextView textShopNum;
    @BindView(R.id.btn_all_order)
    TextView btnAllOrder;
    @BindView(R.id.person_order_grid)
    MyGridView personOrderGrid;
    @BindView(R.id.btn_posters_every_day)
    ImageView btnPostersEveryDay;
    @BindView(R.id.btn_bar_coder)
    LinearLayout btnBarCoder;
    @BindView(R.id.btn_user_un)
    LinearLayout btnUserUn;
    @BindView(R.id.btn_pay_car)
    ImageView btnPayCar;
    @BindView(R.id.btn_online_car)
    LinearLayout btnOnlineCar;
    @BindView(R.id.pay_house_investment)
    LinearLayout payHouseInvestment;
    @BindView(R.id.person_end_grid)
    MyGridView personEndGrid;

    Unbinder unbinder;
    @BindView(R.id.refresh_person_data)
    SwipeRefreshLayout refreshPersonData;
    private View view;
    private MyGridView orderGridView;
    private GridPersonAdapter gridPersonAdapter;
    private MyGridAdapter myGridAdapter;
    private ArrayList<OrderGridBean> orderList = new ArrayList<OrderGridBean>();
    private ArrayList<MyGirdBean> girdBeasList = new ArrayList<MyGirdBean>();

    private Context mContext;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.person_head_item, container, false);
        mContext = getActivity();
        unbinder = ButterKnife.bind(this, view);
        initview();
        return view;
    }


    private void initview() {
        orderGridView = view.findViewById(R.id.person_order_grid);
//        refreshPersonData.measure(0,0);
//        refreshPersonData.setRefreshing(true);
        addGridData();
        addEndGridData();
        viewListener();

    }

    /**
     * 我的订单grid数据
     */
    private void addGridData() {

        orderList.add(new OrderGridBean(R.mipmap.payment_img, "待付款"));
        orderList.add(new OrderGridBean(R.mipmap.delivery_img, "待发货"));
        orderList.add(new OrderGridBean(R.mipmap.take_delivery_img, "待收货"));
        orderList.add(new OrderGridBean(R.mipmap.return_delivery_img, "退换货"));

        gridPersonAdapter = new GridPersonAdapter(orderList, R.layout.gride_person_order_item) {

            @Override
            public void bindView(ViewHolder holder, OrderGridBean obj) {
                holder.setImageResource(R.id.img_icon, obj.getImgId());
                holder.setText(R.id.text_icon, obj.getiName());
                holder.setVisibility(R.id.angle_text_order,View.GONE);
            }
        };
        orderGridView.setAdapter(gridPersonAdapter);

    }

    /**
     * 个人中心底部grid
     */
    private void addEndGridData() {

        girdBeasList.add(new MyGirdBean(R.mipmap.integral_subsidiy_xl_img, "积分明细"));
        girdBeasList.add(new MyGirdBean(R.mipmap.offline_consumption_xl_img, "线下消费明细"));
        girdBeasList.add(new MyGirdBean(R.mipmap.binding_phone_xl_img, "绑定手机号"));
        girdBeasList.add(new MyGirdBean(R.mipmap.shipping_address_xl_img, "收货地址"));
        girdBeasList.add(new MyGirdBean(R.mipmap.friend_management_xl_img, "好友管理"));
        girdBeasList.add(new MyGirdBean(R.mipmap.top_up_center_xl_img, "充值中心"));
        girdBeasList.add(new MyGirdBean(R.mipmap.community_management_xl_img, "社群管理"));

        myGridAdapter = new MyGridAdapter(girdBeasList, R.layout.my_grid_person_end_item) {
            @Override
            public void bindView(ViewHolder holder, MyGirdBean obj) {
                holder.setImageResource(R.id.img_icon, obj.getImgId());
                holder.setText(R.id.text_icon, obj.getiName());
            }
        };
        personEndGrid.setAdapter(myGridAdapter);

    }

    /**
     * 点击事件
     */
    private void viewListener() {
        orderGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(mContext, "你点击了~" + orderList.get(i), Toast.LENGTH_SHORT).show();
            }
        });

        personEndGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(mContext, "你点击了~" + girdBeasList.get(i), Toast.LENGTH_SHORT).show();
            }
        });
        personBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UseTool.intentPage(mContext);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        girdBeasList.clear();
        orderList.clear();
    }
}