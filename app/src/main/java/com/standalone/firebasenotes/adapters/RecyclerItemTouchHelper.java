package com.standalone.firebasenotes.adapters;

import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.standalone.firebasenotes.R;
import com.standalone.firebasenotes.interfaces.ItemInterface;

public class RecyclerItemTouchHelper extends ItemTouchHelper.SimpleCallback {
    final ItemInterface itemInterface;

    public RecyclerItemTouchHelper(ItemInterface itemInterface) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        this.itemInterface = itemInterface;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        final int pos = viewHolder.getAdapterPosition();
        if (direction == ItemTouchHelper.RIGHT) {
            itemInterface.removeItem(pos);
        } else {
            itemInterface.editItem(pos);
        }
    }

    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

        Drawable icon;
        ColorDrawable background;

        View itemView = viewHolder.itemView;
        int backgroundCornerOffset = 20;

        // Initialize icon and background
        if (dX > 0) {
            // Swiping left
            icon = ContextCompat.getDrawable(itemInterface.getContext(), R.drawable.ic_delete);
            background = new ColorDrawable(ContextCompat.getColor(itemInterface.getContext(), R.color.danger_dark));
        } else {
            // Swiping right
            icon = ContextCompat.getDrawable(itemInterface.getContext(), R.drawable.ic_edit);
            background = new ColorDrawable(ContextCompat.getColor(itemInterface.getContext(), R.color.colorPrimaryDark));
        }

        // align icon
        assert icon != null;
        int icMargin = (itemView.getHeight() - icon.getIntrinsicHeight()) / 2;
        int icTop = itemView.getTop() + icMargin;
        int icBottom = icTop + icon.getIntrinsicHeight();

        if (dX > 0) {// Swiping to the right
            int icLeft = itemView.getLeft() + icMargin;
            int icRight = icLeft + icon.getIntrinsicWidth();
            icon.setBounds(icLeft, icTop, icRight, icBottom);

            int bgRight = itemView.getLeft() + ((int) dX) + backgroundCornerOffset;

            background.setBounds(itemView.getLeft(), itemView.getTop(), bgRight, itemView.getBottom());

        } else if (dX < 0) { // Swiping to the left
            int icRight = itemView.getRight() - icMargin;
            int icLeft = icRight - icon.getIntrinsicWidth();
            icon.setBounds(icLeft, icTop, icRight, icBottom);

            int bgLeft = itemView.getRight() + ((int) dX) - backgroundCornerOffset;
            background.setBounds(bgLeft,
                    itemView.getTop(), itemView.getRight(), itemView.getBottom());
        } else {// view is unSwiped
            background.setBounds(0, 0, 0, 0);
        }

        background.draw(c);
        icon.draw(c);
    }


}
