package com.o7solutions.android_6_month

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.appcompat.widget.Toolbar
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import kotlin.math.abs

class MotionLayoutActivity : AppCompatActivity() {

    private var isToolbarCollapsed = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_motion_layout)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        val appBar = findViewById<AppBarLayout>(R.id.app_bar)
        val fab = findViewById<FloatingActionButton>(R.id.fab)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // 1. FAB Interaction with Snackbar
        fab.setOnClickListener { view ->
            Snackbar.make(view, "FAB is anchored to the AppBar", Snackbar.LENGTH_LONG)
                .setAction("Undo", null).show()
        }

        // 2. Monitoring Scroll State
        appBar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            // verticalOffset is 0 when fully expanded, and negative when collapsing
            val totalScrollRange = appBarLayout.totalScrollRange

            if (abs(verticalOffset) >= totalScrollRange) {
                // Fully Collapsed
                if (!isToolbarCollapsed) {
                    isToolbarCollapsed = true
                    fab.hide() // Hide FAB when toolbar is small
                }
            } else {
                // Fully or Partially Expanded
                if (isToolbarCollapsed) {
                    isToolbarCollapsed = false
                    fab.show() // Show FAB when expanded
                }
            }
        })
    }

    // 3. Adding Menu Items for the Toolbar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.bottom_nav_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }
}