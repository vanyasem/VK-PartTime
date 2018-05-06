package ru.semkin.ivan.parttime.ui.base;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import ru.semkin.ivan.parttime.R;

/**
 * Created by Ivan Semkin on 5/7/18
 */
public class BaseActivity extends AppCompatActivity {

    private Toolbar toolbar = null;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        setupToolbar();
    }

    private void setupToolbar() {
        toolbar = findViewById(R.id.toolbar);
        if(toolbar != null) {
            setSupportActionBar(toolbar);
        }
    }

    protected Toolbar getToolbar() {
        return toolbar;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }
}
