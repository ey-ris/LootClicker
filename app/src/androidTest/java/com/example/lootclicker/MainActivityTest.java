package com.example.lootclicker;

import android.content.Context;
import android.content.Intent;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Test
    public void testMainActivityIntentFactory() {
        // Arrange
        Context context = ApplicationProvider.getApplicationContext();
        int userId = 123;

        // Act
        Intent intent = MainActivity.mainActivityIntentFactory(context, userId);

        // Assert
        assertNotNull("Intent should not be null", intent);
        assertEquals("User ID should be passed correctly", userId, intent.getIntExtra("com.example.lootclicker.MAIN_ACTIVITY_USER_ID", -1));
    }

    @Test
    public void testTransitionToLoginActivity() {
        // Arrange
        Context context = ApplicationProvider.getApplicationContext();

        // Act
        Intent intent = LoginActivity.loginIntentFactory(context);

        // Assert
        assertNotNull("Intent should not be null", intent);
        assertEquals("Intent should target LoginActivity", LoginActivity.class.getName(), intent.getComponent().getClassName());
    }
}