package com.vivian.Weather;

import android.graphics.Canvas;
import android.util.Log;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class myItemTouchHelperCallBack extends ItemTouchHelper.Callback {
    private boolean bActive = false;
    private boolean isRight = false;
    private boolean isLeft = false;
    private androidx.recyclerview.widget.RecyclerView.ViewHolder lastViewHolder = null;
    private ItemTouchHelperAdapter itemTouchHelperAdapter;

    public myItemTouchHelperCallBack(ItemTouchHelperAdapter itemTouchHelperAdapter) {
        this.itemTouchHelperAdapter = itemTouchHelperAdapter;
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        //允许上下拖动
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        //允许从右向左滑动
        int swipeFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        //onItemMove接口里的方法
        itemTouchHelperAdapter.onItemMove(viewHolder.getBindingAdapterPosition(), target.getBindingAdapterPosition());
        return true;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        //onItemDelete接口里的方法
        //  itemTouchHelperAdapter.onItemDelete(viewHolder.getBindingAdapterPosition());
    }

    @Override
    public boolean isLongPressDragEnabled() {
        //该方法返回值为true时，表示支持长按ItemView拖动
        return true;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        //该方法返回true时，表示如果用户触摸并且左滑了view，那么可以执行滑动删除操作，就是可以调用onSwiped()方法
        return true;
    }

    //限制ImageView长度所能增加的最大值
    private double ICON_MAX_SIZE = 50;
    //ImageView的初始长宽
    private int fixedWidth = 150;

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        //Log.i("tt","clearview");
        super.clearView(recyclerView, viewHolder);
        int i = getSlideLimitation(viewHolder);
        int j = viewHolder.itemView.getScrollX();
        // 滑动结束后触发


        if (viewHolder.itemView.getScrollX() >= getSlideLimitation(viewHolder) / 2)
            viewHolder.itemView.scrollTo(getSlideLimitation(viewHolder), 0);
        else
            viewHolder.itemView.scrollTo(0, 0);


    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        //仅对侧滑状态下的效果做出改变

       // Log.i("tt", "onchilddraw : " + "dx:" + dX + "getScrollX()" + viewHolder.itemView.getScrollX());
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            if (isRight) {
                if (bActive == true) {
                    if (viewHolder.itemView.getScrollX() <= getSlideLimitation(viewHolder)) {
                        viewHolder.itemView.scrollTo((int) (getSlideLimitation(viewHolder) - dX), 0);
                    }
                }
            } else {
                if (dX > 0) {
                    viewHolder.itemView.scrollTo(-(int) dX, 0);
                } else {
                    if (bActive == true) {
                        //如果dX小于等于删除方块的宽度，那么我们把该方块滑出来
                        if (Math.abs(dX) <= getSlideLimitation(viewHolder)) {
                            viewHolder.itemView.scrollTo(-(int) dX, 0);
                        } else {
                            viewHolder.itemView.scrollTo(getSlideLimitation(viewHolder), 0);
                        }
                    }
                }
            }

        } else if (actionState == ItemTouchHelper.ACTION_STATE_DRAG) {
            //拖拽状态下不做改变，需要调用父类的方法
            if (isRight) {
                viewHolder.itemView.scrollTo(0, 0);
            } else
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    }

    /**
     * 获取删除方块的宽度
     */
    public int getSlideLimitation(RecyclerView.ViewHolder viewHolder) {
        ViewGroup viewGroup = (ViewGroup) viewHolder.itemView;
        //Log.i("tt", "width : " + viewGroup.getChildAt(1).getLayoutParams().width);
        return viewGroup.getChildAt(1).getLayoutParams().width;

    }

    /**
     * Item被选中时候回调
     *
     * @param viewHolder
     * @param actionState 当前Item的状态
     *                    ItemTouchHelper.ACTION_STATE_IDLE   闲置状态
     *                    ItemTouchHelper.ACTION_STATE_SWIPE  滑动中状态
     *                    ItemTouchHelper#ACTION_STATE_DRAG   拖拽中状态
     */
    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        //Log.i("tt", "onSelectedChanged : " + actionState);

        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            if (viewHolder != lastViewHolder) {
                if (lastViewHolder != null) {
                    lastViewHolder.itemView.scrollTo(0, 0);
                }
                lastViewHolder = viewHolder;
            }

            bActive = true;
            //准备开始滑动
            if (viewHolder.itemView.getScrollX() > 0) {
                //滑块将要向右移动
                isRight = true;

            } else {
                //滑块将要向右移动
                isRight = false;

            }

        } else {
            bActive = false;
        }
        super.onSelectedChanged(viewHolder, actionState);
    }

    @Override
    public float getSwipeThreshold(RecyclerView.ViewHolder viewHolder) {
        //设置滑动删除最大距离，1.5代表是itemview宽度的1.5倍,目的是不让它删除
        return 1f;
    }

    @Override
    public float getSwipeEscapeVelocity(float defaultValue) {
        //设置滑动速度，目的是不让它进入onSwiped
        return defaultValue * 100;
    }

}



