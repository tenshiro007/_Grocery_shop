package com.example.meimall;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.example.meimall.GroceryItemActiviy.GROCERY_ITEM_KEY;

public class AddReviewDialog extends DialogFragment {

    private AddReview addReview;
    private TextView txtItemName, txtWarning;
    private EditText editReview, editUserName;
    private Button btnAddReview;

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_add_review, null, false);
        initView(view);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setView(view);


        Bundle bundle = getArguments();
        if (null != bundle) {
            GroceryItem items = bundle.getParcelable(GROCERY_ITEM_KEY);
            if (null != items) {
                txtItemName.setText(items.getName());
                btnAddReview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String username = editUserName.getText().toString();
                        String review = editReview.getText().toString();
                        String date = getCurrentDate();
                        if (username.equals("") || review.equals("")) {
                            txtWarning.setText("Fill all the blanks");
                            txtWarning.setVisibility(View.VISIBLE);
                        } else {
                            txtWarning.setVisibility(View.GONE);
                            try {
                                addReview = (AddReview) getActivity();
                                addReview.onAddReviewResult(new Review(items.getId(), username, review, date));
                                dismiss();
                            } catch (ClassCastException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
        }
        return builder.create();

//        return super.onCreateDialog(savedInstanceState);
    }

    private String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
        return sdf.format(calendar.getTime());
    }

    private void initView(View view) {
        txtItemName = view.findViewById(R.id.txtItemName);
        txtWarning = view.findViewById(R.id.txtWarning);
        editReview = view.findViewById(R.id.editTxtReview);
        editUserName = view.findViewById(R.id.editTxtUsername);
        btnAddReview = view.findViewById(R.id.btnAddReview);
    }

    public interface AddReview {
        void onAddReviewResult(Review review);
    }
}
