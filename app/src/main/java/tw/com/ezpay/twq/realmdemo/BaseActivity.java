package tw.com.ezpay.twq.realmdemo;

import android.support.v7.app.AppCompatActivity;

import com.afollestad.materialdialogs.MaterialDialog;

public class BaseActivity extends AppCompatActivity{

    private MaterialDialog progressDialog;

    protected void showProgressDialog() {
        showProgressDialog(R.string.progress_dialog, R.string.please_wait);
    }

    protected void showProgressDialog(int title, int content) {
        showProgressDialog(
                getResources().getString(title),
                getResources().getString(content));
    }

    protected void showProgressDialog(String title, String content) {

        if (progressDialog == null)

            progressDialog = new MaterialDialog.Builder(this)
                    .title(title)
                    .content(content)
                    .progress(true, 0)
                    .cancelable(false)
                    .show();
        else
            progressDialog.show();
    }

    protected void hideProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    protected void showHintDialog(int content) {
        showHintDialog(getResources().getString(content));
    }

    protected void showHintDialog(String content) {

        new MaterialDialog.Builder(this)
                .content(content)
                .positiveText(getString(R.string.confirm))
                .show();
    }


    protected void showError(Throwable throwable) {

        if (progressDialog != null) {
            progressDialog.dismiss();
        }

        new MaterialDialog.Builder(this)
                .title(R.string.error)
                .content(throwable.getLocalizedMessage())
                .positiveText(R.string.close)
                .show();
    }
}
