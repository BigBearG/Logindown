package text.login;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {
    private downlineRecevier downlineRecevier;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction("text.login.downline.TRUE");
        downlineRecevier=new downlineRecevier();
        registerReceiver(downlineRecevier,intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (downlineRecevier!=null){
            unregisterReceiver(downlineRecevier);
            downlineRecevier=null;
        }
    }

    class downlineRecevier extends BroadcastReceiver{

        @Override
        public void onReceive(final Context context, Intent intent) {
            AlertDialog.Builder builder=new AlertDialog.Builder(context);
            builder.setTitle("警告：");
            builder.setCancelable(false);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ActivityCollector.finishAlll();
                    Intent intent=new Intent(context,LoginActivity.class);
                    context.startActivity(intent);

                }
            });
            builder.show();
        }
    }
}
