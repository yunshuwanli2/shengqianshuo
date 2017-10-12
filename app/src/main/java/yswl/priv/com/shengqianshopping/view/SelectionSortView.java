package yswl.priv.com.shengqianshopping.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import yswl.priv.com.shengqianshopping.R;

/**
 * Created by Administrator on 2017/10/12.
 */

public class SelectionSortView extends LinearLayout {


    @BindView(R.id.selectionsortview_tv_selectioncontent)
    TextView tvSelectioncontent;
    @BindView(R.id.selectionsortview_img_status)
    ImageView imgStatus;

    private boolean sortAsc = false;
    private int position;

    public SelectionSortView(Context context) {
        super(context);
    }

    public SelectionSortView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View view = LayoutInflater.from(context).inflate(R.layout.view_selectionsort_layout,
                this);
        ButterKnife.bind(this, view);
    }

    public void onClick(int position) {
        if (this.position == position) {
            sortAsc = !sortAsc;
        }
        imgStatus.setVisibility(View.VISIBLE);
        if (sortAsc) {
            imgStatus.setImageResource(R.mipmap.icon_ace);
        } else {
            imgStatus.setImageResource(R.mipmap.icon_desc);
        }

    }

    public boolean isSortAsc() {
        return sortAsc;
    }

    public void setSortAsc(boolean sortAsc) {
        this.sortAsc = sortAsc;
    }

    //设置排序方式
    public void setContent(String sortType) {
        tvSelectioncontent.setText(sortType);
    }


    //获取控件
    public ImageView getImgStatus() {
        return imgStatus;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    //默认设置
    public void setDefault() {
        sortAsc = false;
        imgStatus.setVisibility(View.VISIBLE);
        imgStatus.setImageResource(R.mipmap.icon_desc);
    }
    //隐藏图片
    public void hideImg(){
        imgStatus.setVisibility(View.INVISIBLE);
    }

}
