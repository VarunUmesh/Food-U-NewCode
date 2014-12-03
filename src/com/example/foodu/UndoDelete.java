package com.example.foodu;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.graphics.Color;
import android.os.Handler;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.widget.Button;
import android.widget.TextView;

public class UndoDelete {

	private View mUndoView;
    private TextView mMessageView;
    private TextView mUndoButton;
    private ViewPropertyAnimator mUndoViewAnimator;
    private Handler mUndoHandler = new Handler();
    private UndoListener mUndoListener;

    public interface UndoListener {
        void onUndo();
    }

    public UndoDelete(View view, UndoListener undoListener) {
    	mUndoView = view;
        mUndoViewAnimator = mUndoView.animate();
        mUndoListener = undoListener;

        mMessageView = (TextView) mUndoView.findViewById(R.id.undotext);
        mMessageView.setTextColor(Color.WHITE);
        mUndoButton = (TextView) mUndoView.findViewById(R.id.undobutton);
        mUndoButton.setText("UNDO");
        mUndoButton.setTextColor(Color.YELLOW);
        mUndoButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        hideUndo();
                        mUndoListener.onUndo();
                    }
                });

        hideUndo();
    }

    public void showUndo(String message) {
        mMessageView.setText(message);

        mUndoHandler.removeCallbacks(mUndoRunnable);
        mUndoHandler.postDelayed(mUndoRunnable,
               5000);

        mUndoView.setVisibility(View.VISIBLE);
 
            mUndoViewAnimator.cancel();
            mUndoViewAnimator
                    .alpha(1)
                    .setDuration(
                    		mUndoView.getResources()
                                    .getInteger(android.R.integer.config_longAnimTime))
                    .setListener(null);
        
    }

    public void hideUndo() {
        mUndoHandler.removeCallbacks(mUndoRunnable);
 
            mUndoViewAnimator.cancel();
            mUndoViewAnimator
                    .alpha(0)
                    .setDuration(mUndoView.getResources()
                            .getInteger(android.R.integer.config_longAnimTime))
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            mUndoView.setVisibility(View.GONE);
                        }
                    });
        }


    private Runnable mUndoRunnable = new Runnable() {
        @Override
        public void run() {
            hideUndo();
        }
    };
}
