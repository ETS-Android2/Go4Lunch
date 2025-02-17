package com.berete.go4lunch.ui.core.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import com.berete.go4lunch.R;
import com.berete.go4lunch.domain.shared.models.User;
import com.berete.go4lunch.ui.restaurant.details.RestaurantDetailsActivity;

import java.util.Calendar;

import javax.annotation.Nullable;

public class LunchTimeNotification {

  private final Context context;
  private String restaurantAddress;
  private User currentUser;
  private final StringBuilder otherParticipantsListBuilder = new StringBuilder();

  private static final String NOTIFICATION_CHANNEL_ID =
      LunchTimeNotificationService.class.getName() + "CHANNEL_ID";

  public LunchTimeNotification(Context context) {
    this.context = context;
  }

  public LunchTimeNotification setCurrentUser(User currentUser) {
    this.currentUser = currentUser;
    return this;
  }

  public LunchTimeNotification setLunchParticipants(User[] participants) {
    assert currentUser != null;
    final boolean theCurrentUserIsAlone = participants.length < 2;
    if (theCurrentUserIsAlone) return this;
    for (int i = 0; i < participants.length; i++) {
      if (participants[i].getId().equals(currentUser.getId())) {
        continue;
      }
      otherParticipantsListBuilder.append(participants[i].getUsername()).append(", ");
    }
    final int builderLength = otherParticipantsListBuilder.length();
    otherParticipantsListBuilder.delete(builderLength - 2, builderLength); // remove the last comma
    return this;
  }

  public LunchTimeNotification setRestaurantAddress(String restaurantAddress) {
    this.restaurantAddress = restaurantAddress;
    return this;
  }

  public LunchTimeNotification createNotificationChanelIfNeeded(
      NotificationManager notificationManager) {
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
      final String notificationChanelName = context.getString(R.string.lunch_reminder_time);
      final String notificationChanelDescription =
          context.getString(R.string.notification_channel_description);
      final NotificationChannel notificationChannel =
          new NotificationChannel(
              NOTIFICATION_CHANNEL_ID, notificationChanelName, NotificationManager.IMPORTANCE_HIGH);
      notificationChannel.setDescription(notificationChanelDescription);
      notificationManager.createNotificationChannel(notificationChannel);
    }
    return this;
  }

  public Notification build() {
    return new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
        .setContentTitle(context.getString(R.string.lunch_time_notification_title))
        .setContentText(buildNotificationContentText())
        .setStyle(new NotificationCompat.BigTextStyle().bigText(buildNotificationContentText()))
        .setSmallIcon(R.drawable.ic_restaurant)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setContentIntent(getPendingIntent())
        .build();
  }

  public PendingIntent getPendingIntent() {
    final Intent intent = new Intent(context, RestaurantDetailsActivity.class);
    return PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
  }

  private String buildNotificationContentText() {
    final String otherParticipantsNames = otherParticipantsListBuilder.toString();
    if (otherParticipantsNames.isEmpty()) {
      return context.getString(
          R.string.lunch_time_notification_msg,
          currentUser.getUsername(),
          currentUser.getChosenRestaurantName(),
          restaurantAddress);
    }
    return context.getString(
        R.string.lunch_time_notification_msg_with_workmates,
        currentUser.getUsername(),
        currentUser.getChosenRestaurantName(),
        restaurantAddress,
        otherParticipantsNames);
  }

  public static void setEnabled(Context context, boolean enabled, @Nullable Calendar calendar) {
    final LunchAlarmManager lunchAlarmManager = new LunchAlarmManager(context);
    if (enabled) {
      if (calendar == null) throw new IllegalArgumentException();
      BootReceiver.enable(context);
      lunchAlarmManager.scheduleAlarm(calendar, LunchAlarmReceiver.class);
    } else {
      BootReceiver.disable(context);
      lunchAlarmManager.cancelAlarm(LunchAlarmReceiver.class);
    }
  }
}
