package sksa.aa.tweaker;

/**
 * SQL command definitions for all tweaks.
 * Each tweak has one or more SQL INSERT statements that modify the phenotype database.
 */
public class TweakDefinitions {

    // ========== Coolwalk Tweaks ==========

    public static final String[] COOLWALK_ENABLE = {
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'Coolwalk__enabled', NULL, '1', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'Assistant__coolwalk_suggestions_grpc_enabled', NULL, '1', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'Coolwalk__media_rec_card_enabled', NULL, '1', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'Coolwalk__opt_in _default', NULL, '1', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'Coolwalk__rail_dock_enabled', NULL, '1', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'Coolwalk__rail_dock_four_app_enabled', NULL, '1', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'Coolwalk__rail_widget_enabled', NULL, '1', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'Coolwalk__allow_focus_input', NULL, '1', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'Coolwalk__assistant_media_rec_shortcut_enabled', NULL, '1', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'Coolwalk__assistant_suggestions_enabled', NULL, '1', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'Coolwalk__canonical_vertical_rail_default', NULL, '1', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'Coolwalk__choose_assistant_suggestion_over_app_suggestion', NULL, '1', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'Media__coolwalk_playback_gradient_scrim_enabled', NULL, '1', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'Media__favorites_button_enabled', NULL, '1', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'Coolwalk__streamed_media_recommendations_enabled', NULL, '1', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'Media__foreground_search_fab_enabled', NULL, '1', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'Coolwalk__a4c_suggestions_kill_switch', NULL, '0', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'Coolwalk__rotary_proximity_navigation', NULL, '0', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'Coolwalk__semi_wide_vertical_enabled', NULL, '1', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'Coolwalk__short_canonical_vertical_enabled', NULL, '1', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'Coolwalk__three_actions_hun_ui_enabled', NULL, '1', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'Coolwalk__indicate_severe_thermal_status', NULL, '1', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'Coolwalk__focus_check_kill_switch', NULL, '0', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'Coolwalk__fix_status_bar_highlight_ghosting_kill_switch', NULL, '0', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'Coolwalk__use_widescreen_crossfade', NULL, '0', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'Coolwalk__dashboard_placement_customization_enabled', NULL, '1', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'Coolwalk__media_notification_high_priority_kill_switch', NULL, '0', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'Coolwalk__launcher_settings_kill_switch', NULL, '0', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'Weather__enabled', NULL, '1', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'Weather__icon_enabled', NULL, '1', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'Weather__preinstalled_frx_toggle_enabled', NULL, '1', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'Boardwalk__news_browser_available', NULL, '1', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'SystemUi__car_ui_entry_use_configuration_context_kill_switch', NULL, '0', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'SystemUi__media_switcher_page_while_started_kill_switch', NULL, '0', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'SystemUi__projection_notification_hun_sbn_converter_hack_kill_switch', NULL, '0', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'SystemUi__rail_assistant_media_rec_enabled_on_focus_screens', NULL, '1', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'SystemUi__wallpaper_backdrop_enabled', NULL, '1', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'AppListUi__use_updated_calendar_ui', NULL, '1', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'CarAppLibrary__is_toggle_allowed_in_map_and_pane_templates_kill_switch', NULL, '0', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'CarAppLibrary__messaging_aap_host_logic_enabled', NULL, '1', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'CarAppLibrary__tab_template_enabled', NULL, '1', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'CompanionDeviceManager__integration_enabled', NULL, '1', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'Coolwalk__allow_all_inputs_kill_switch', NULL, '0', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'Media__favorites_button_enabled', NULL, '1', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'Media__show_album_art_for_suggestion', NULL, '1', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'Media__show_settings_button_in_browse_view', NULL, '1', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'Media__tint_resource_uris', NULL, '', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'CarAppLibrary__radio_buttons_ui_changes_enabled', NULL, '1', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'Coolwalk__improve_startup', NULL, '1', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'Media__custom_action_assert_connection_kill_switch', NULL, '0', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'Coolwalk__rail_widget_user_education_enabled', NULL, '1', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'Coolwalk__navigation_signal_to_assistant_enabled', NULL, '1', 0);"
    };

    public static final String[] COOLWALK_DISABLE = {
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'Coolwalk__enabled', NULL, '0', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'Assistant__coolwalk_suggestions_grpc_enabled', NULL, '0', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'Coolwalk__fishfood_nag_enabled', NULL, '0', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'Coolwalk__media_rec_card_enabled', NULL, '0', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'Coolwalk__opt_in _default', NULL, '0', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'Coolwalk__rail_dock_enabled', NULL, '0', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'Coolwalk__rail_dock_four_app_enabled', NULL, '0', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'Coolwalk__rail_widget_enabled', NULL, '0', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'Coolwalk__allow_focus_input', NULL, '0', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'Coolwalk__assistant_media_rec_shortcut_enabled', NULL, '0', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'Coolwalk__assistant_suggestions_enabled', NULL, '0', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'Coolwalk__canonical_vertical_rail_default', NULL, '0', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'Coolwalk__canonical_vertical_rail_enabled', NULL, '0', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'Coolwalk__choose_assistant_suggestion_over_app_suggestion', NULL, '0', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'Media__coolwalk_playback_gradient_scrim_enabled', NULL, '0', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'Media__favorites_button_enabled', NULL, '0', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'Coolwalk__streamed_media_recommendations_enabled', NULL, '0', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'Coolwalk__a4c_suggestions_kill_switch', NULL, '0', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'Coolwalk__rotary_proximity_navigation', NULL, '0', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'Coolwalk__semi_wide_vertical_enabled', NULL, '0', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'Coolwalk__short_canonical_vertical_enabled', NULL, '0', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'Coolwalk__three_actions_hun_ui_enabled', NULL, '0', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'Coolwalk__indicate_severe_thermal_status', NULL, '0', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'Coolwalk__use_widescreen_crossfade', NULL, '0', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'Coolwalk__dashboard_placement_customization_enabled', NULL, '0', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'Coolwalk__media_notification_high_priority_kill_switch', NULL, '0', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'Coolwalk__add_boardwalk_theme_attrs_kill_switch', NULL, '0', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'Coolwalk__choreograph_start_composition_kill_switch', NULL, '0', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'Coolwalk__rail_hotseat_check_app_available_kill_switch', NULL, '0', 0);"
    };

    // ========== Coolwalk Day/Night Tweaks ==========

    public static final String[] COOLWALK_DAYNIGHT_ENABLE = {
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'Coolwalk__day_night_theme_enabled', NULL, '1', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'Coolwalk__enable_palette_swap_by_broadcast', NULL, '1', 0);"
    };

    // ========== Assistant Tips ==========

    public static final String[] ASSISTANT_TIPS_ENABLE = {
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'LauncherShortcuts__assistant_shortcut_enabled', NULL, '1', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'LauncherApps__clean_up_cujs_kill_switch', NULL, '1', 0);"
    };

    // ========== Material You ==========

    public static final String[] MATERIAL_YOU_ENABLE = {
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'SystemUi__material_you_settings_enabled', NULL, '1', 0);"
    };

    // ========== Decline SMS ==========

    public static final String[] DECLINE_SMS_ENABLE = {
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'Messaging__decline_call_message_enabled', NULL, '1', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'Messaging__template_ui_enabled', NULL, '1', 0);"
    };

    // ========== Inertial Scroll ==========

    public static final String[] INERTIAL_SCROLL_ENABLE = {
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'SystemUi__inertial_scrolling_enabled', NULL, '1', 0);"
    };

    // ========== Vertical Bar ==========

    public static final String[] VERTICAL_BAR_ENABLE = {
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'SystemUi__horizontal_rail_canonical_breakpoint_dp', NULL, '40', 0);"
    };

    // ========== Battery Outline ==========

    public static final String[] BATTERY_OUTLINE_DISABLE = {
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'BatterySaver__icon_outline_enabled', NULL, '0', 0);"
    };

    // ========== Battery Warning ==========

    public static final String[] BATTERY_WARNING_DISABLE = {
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'BatterySaver__warning_enabled', NULL, '0', 0);"
    };

    // ========== Message Auto Read ==========

    public static final String[] MESSAGE_AUTOREAD_ENABLE = {
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'Messaging__voice_messages_read_enabled', NULL, '1', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'Messaging__direct_reply_enabled', NULL, '1', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'Messaging__autoplay_messages_enabled', NULL, '1', 0);"
    };

    // ========== Multi Display ==========

    public static final String[] MULTI_DISPLAY_ENABLE = {
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'MultiDisplay__enabled', NULL, '1', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'MultiDisplay__clustersim_enabled', NULL, '1', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'MultiDisplay__gal_munger_enabled', NULL, '1', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'MultiDisplay__cluster_launcher_enabled', NULL, '1', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'MultiDisplay__cluster_launcher_enabled', NULL, '1', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'MultiDisplay__aux_display_default_configuration', NULL, '1', 0);"
    };

    // ========== No Bluetooth Auto-Connect ==========

    public static final String[] BLUETOOTH_AUTOCONNECT_DISABLE = {
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.gms.car', 0,'BluetoothPairing__car_bluetooth_service_disable', NULL, '1', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.gms.car', 0,'BluetoothPairing__car_bluetooth_service_skip_pairing', NULL, '1', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.gms.car', 0,'BluetoothPairing__connect_bluetooth_timeout', NULL, '1', 0);"
    };

    // ========== New Seekbar (Tappable Progress) ==========

    public static final String[] NEW_SEEKBAR_ENABLE = {
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'Media__tappable_progress_bar_enabled', NULL, '1', 0);"
    };

    // ========== Disable Telemetry ==========

    public static final String[] TELEMETRY_PRE_COMMANDS = {
        "-- DELETE FROM Flags WHERE name LIKE '%telemetry%' AND config_package_name='com.google.android.projection.gearhead';",
        "-- DELETE FROM Flags WHERE name LIKE '%telemetry%' AND config_package_name='com.google.android.gms.car';"
    };

    public static final String[] TELEMETRY_DISABLE = {
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.gms.car', 0,'CarEventLoggerRefactorFeature__convert_car_setup_analytics_telemetry', NULL, '0', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.gms.car', 0,'CarServiceTelemetry__enabled', NULL, '0', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.gms.car', 0,'CarServiceTelemetry__is_wifi_kbps_logging_enabled', NULL, '0', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.gms.car', 0,'CarServiceTelemetry__log_battery_temperature', NULL, '0', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.gms.car', 0,'CarServiceTelemetry__wifi_latency_log_frequency_ms', NULL, '99999999', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.gms.car', 0,'ConnectivityLogging__heartbeat_interval_ms', NULL, '99999999', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.gms.car', 0,'TelemetryDriveIdFeature__enable_log_event_validation', NULL, '0', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.gms.car', 0,'TelemetryDriveIdFeature__enabled', NULL, '0', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.gms.car', 0,'UsbStatusLoggingFeature__monitor_usb_ping_telemetry_enabled', NULL, '0', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'TelemetryDriveIdForGearheadFeature__enable_frx_setup_logging_via_gearhead', NULL, '0', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.gms.car', 0,'AudioStatsLoggingFeature__audio_stats_logging_period_milliseconds', NULL, '99999999', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.gms.car', 0,'FrameworkMediaStatsLoggingFeature__is_media_stats_queue_time_logging_enabled', NULL, '0', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'ConnectivityLogging__num_background_threads', NULL, '0', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'ConnectivityLogging__include_extra_events', NULL, '0', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'ConnectivityLogging__enable_heartbeat', NULL, '0', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'WifiChannelLogging__enabled', NULL, '0', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'ConnectivityLogging__session_info_dump_size', NULL, '0', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'BluetoothMetadataLogger__enabled', NULL, '0', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.gms.car', 0,'CarEventLoggerRefactorFeature__convert_car_analytics_telemetry', NULL, '0', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'Bugfix__sensitive_permissions_extra_logging', NULL, '0', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'ConnectivityLogging__log_bluetooth_rssi', NULL, '0', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'ConnectivityLogging__save_log_when_usb_starts', NULL, '0', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'ConnectivityLogging__skip_retroactive_usb_logging', NULL, '1', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'InternetConnectivityLogging__enabled', NULL, '0', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'Telemetry__local_logging', NULL, '0', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'WirelessProjectionInGearhead__wireless_wifi_additional_start_logging', NULL, '0', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'Dialer__r_telemetry_enabled', NULL, '0', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'AssistantSilenceDiagnostics__enabled', NULL, '0', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'TelemetryDriveIdForGearheadFeature__enable_continuous_telemetry_binding', NULL, '0', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'TelemetryDriveIdForGearheadFeature__enable_telemetry_impl_conversion', NULL, '0', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'ConnectivityLogging__long_session_timeout_ms', NULL, '1', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'ConnectivityLogging__short_session_timeout_ms', NULL, '1', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'ConnectivityLogging__session_timeout_ms', NULL, '1000', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'PhenotypeCache__load_snapshot', NULL, '0', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'PhenotypeCache__save_snapshot', NULL, '0', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'PhenotypeCache__use_snapshot', NULL, '0', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'Performance__log_to_telemetry', NULL, '0', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'Performance__primes_network_metrics_enabled_kill_switch', NULL, '0', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'ConnectivityLogging__use_realtime_if_invalid', NULL, '1', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'Performance__primes_logging_enabled', NULL, '1', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'Telemetry__westworld_logging_enabled_kill_switch', NULL, '1', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.gms.car', 0,'enable_blueooth_fsm_telemetry', NULL, '0', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.gms.car', 0,'Performance__use_optimized_car_activities', NULL, '0', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.gms.car', 0,'Messaging__assistant_notification_data_sharing_enabled', NULL, '0', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.gms.car', 0,'CarProjectionValidator__measure_latency_enabled', NULL, '0', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.gms.car', 0,'PhenotypeProcessStableFlags__first_read_latency', NULL, '0', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.gms.car', 0,'PhenotypeProcessStableFlags__legacy_flag_infrastructure_enabled', NULL, '1', 0);"
    };

    // ========== Six Tap / Touch Limit ==========
    // Note: This is a large tweak - SQL extracted from patchfortouchlimit()

    public static final String[] SIX_TAP_PRE_COMMANDS = {
        "-- DELETE FROM Flags WHERE name LIKE '%speedbump%';"
    };

    public static final String[] SIX_TAP_ENABLE = {
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'ContentBrowse__drawer_default_allowed_taps_touchpad', NULL, '999', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'ContentBrowse__enable_speed_bump_projected', NULL, '0', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'ContentBrowse__keyboard_force_disabled', NULL, '0', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'Dialer__speedbump_enabled', NULL, '0', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'Mesquite__speedbump_enabled', NULL, '0', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'ContentBrowse__speedbump_force_enabled', NULL, '0', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'McFly__speedbump_enabled', NULL, '0', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'Media__projected_speedbump_enabled', NULL, '0', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'Watevra__speedbump_enabled', NULL, '0', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'Watevra__speedbump_map_interactivity_events_enabled', NULL, '1', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'Watevra__speedbump_non_scroll_events_enabled', NULL, '1', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'ContentBrowse__sixtap_force_enabled', NULL, '0', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'ContentBrowse__permits_chart', NULL, '1', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'ContentBrowse__use_updated_list_view_kill_switch', NULL, '1', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'TouchpadUiNavigation__multimove_penalty_mm', NULL, '0', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'Watevra__speedbump_max_list_size', NULL, '400', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'Watevra__max_list_size', NULL, '400', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'Watevra__speedbump_max_grid_list_size', NULL, '300', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'Watevra__max_grid_list_size', NULL, '300', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'Watevra__speedbump_map_interactivity_enabled', NULL, '1', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'CarAppLibrary__max_list_size_with_speedbump', NULL, '300', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'CarAppLibrary__add_default_screen_size_value_kill_switch', NULL, '1', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'CarAppLibrary__allow_long_text_while_parked_kill_switch', NULL, '1', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'CarAppLibrary__allow_secondary_actions_in_half_lists', NULL, '1', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'CarAppLibrary__cluster_enabled', NULL, '1', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'CarAppLibrary__list_template_fab_enabled', NULL, '1', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'CarAppLibrary__grid_template_fab_enabled', NULL, '1', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'CarAppLibrary__tab_template_enabled', NULL, '1', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'CarAppLibrary__task_limit_restrictions_allows_overflow', NULL, '1', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'AppQualityTester__developer_setting_enabled', NULL, '1', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'Assistant__transcription_enabled', NULL, '1', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'CarAppLibrary__app_driven_refresh_enabled', NULL, '0', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'CarAppLibrary__app_driven_refresh_enabled_for_undefined_category', NULL, '0', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'CarAppLibrary__allow_secondary_actions_in_half_lists', NULL, '1', 0);"
    };

    // ========== Speed Hack (Parked Mode) ==========

    public static final String[] SPEED_HACK_ENABLE = {
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'CarSensorParameters__max_parked_speed_gps_sensor', NULL, '999', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'CarSensorParameters__max_parked_speed_wheel_sensor', NULL, '999', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'VisualPreview__unchained', NULL, '1', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'VisualPreview__chained', NULL, '0', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'VisualPreview__unchained_experiment_id', NULL, '1', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'GearSnacks__parked_gears', NULL, '999', 0);"
    };

    // ========== Patched Apps ==========
    // Note: This is complex and requires dynamic SQL generation for the app whitelist
    // See TweakManager.applyTweakWithAppList() for handling

    // ========== Force Widescreen ==========

    public static final String[] FORCE_WIDESCREEN_ENABLE = {
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'SystemUi__widescreen_breakpoint_dp', NULL, '470', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'SystemUi__rail_assistant_media_rec_enabled_min_screen_width', NULL, '470', 0);"
    };

    public static final String[] FORCE_NO_WIDESCREEN_ENABLE = {
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'SystemUi__regular_layout_max_width_dp', NULL, '1919', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'SystemUi__semi_widescreen_breakpoint_dp', NULL, '1920', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'SystemUi__widescreen_breakpoint_dp', NULL, '2000', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'SystemUi__short_portrait_breakpoint_dp', NULL, '1920', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'SystemUi__portrait_breakpoint_dp', NULL, '1920', 0);"
    };

    public static final String[] FORCE_PORTRAIT_ENABLE = {
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'SystemUi__short_portrait_breakpoint_dp', NULL, '10', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'SystemUi__portrait_breakpoint_dp', NULL, '10', 0);",
        "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'SystemUi__widescreen_breakpoint_dp', NULL, '3000', 0);"
    };

    public static String[] getForceWideScreenSql( int value ) {
        switch ( value ) {
            case 10: return FORCE_PORTRAIT_ENABLE;
            case 470: return FORCE_WIDESCREEN_ENABLE;
            case 1920: return FORCE_NO_WIDESCREEN_ENABLE;
            default: return FORCE_WIDESCREEN_ENABLE;
        }
    }

    public static String getForceWideScreenTriggerName( int value ) {
        switch ( value ) {
            case 10: return "force_portrait";
            case 470: return "force_ws";
            case 1920: return "force_no_ws";
            default: return "force_ws";
        }
    }

    // ========== HUN Duration ==========
    // Note: Dynamic values - see TweakManager.applyTweakWithIntValue()

    public static String buildHunDurationSql(int durationMs) {
        return "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'SystemUi__hun_default_heads_up_timeout_ms', NULL, '" + durationMs + "', 0);";
    }

    public static String buildMediaHunDurationSql(int durationMs) {
        return "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'SystemUi__media_hun_in_rail_widget_timeout_ms', NULL, '" + durationMs + "', 0);";
    }

    // ========== USB Bitrate ==========
    // Note: Dynamic values based on multiplier

    public static String[] buildUSBitrateSql(double multiplier) {
        return new String[] {
            "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.gms.car', 0,'VideoEncoderParamsFeature__bitrate_1080p', NULL, '" + String.format("%.0f", 8000000 * multiplier) + "', 0);",
            "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.gms.car', 0,'VideoEncoderParamsFeature__bitrate_1080p_hevc', NULL, '" + String.format("%.0f", 2000000 * multiplier) + "', 0);",
            "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.gms.car', 0,'VideoEncoderParamsFeature__bitrate_480p', NULL, '" + String.format("%.0f", 2000000 * multiplier) + "', 0);",
            "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.gms.car', 0,'VideoEncoderParamsFeature__bitrate_480p_hevc', NULL, '" + String.format("%.0f", 750000 * multiplier) + "', 0);",
            "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.gms.car', 0,'VideoEncoderParamsFeature__bitrate_720p', NULL, '" + String.format("%.0f", 4000000 * multiplier) + "', 0);",
            "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.gms.car', 0,'VideoEncoderParamsFeature__bitrate_720p_hevc', NULL, '" + String.format("%.0f", 1500000 * multiplier) + "', 0);"
        };
    }

    // ========== USB Bitrate (with _usb suffix) ==========

    public static String[] buildUSBBitrateSql(double multiplier) {
        return new String[] {
            "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.gms.car', 0,'VideoEncoderParamsFeature__bitrate_1080p_usb', NULL, '" + String.format("%.0f", 16000000 * multiplier) + "', 0);",
            "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.gms.car', 0,'VideoEncoderParamsFeature__bitrate_1080p_usb_hevc', NULL, '" + String.format("%.0f", 3000000 * multiplier) + "', 0);",
            "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.gms.car', 0,'VideoEncoderParamsFeature__bitrate_480p_usb', NULL, '" + String.format("%.0f", 8000000 * multiplier) + "', 0);",
            "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.gms.car', 0,'VideoEncoderParamsFeature__bitrate_480p_usb_hevc', NULL, '" + String.format("%.0f", 1000000 * multiplier) + "', 0);",
            "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.gms.car', 0,'VideoEncoderParamsFeature__bitrate_720p_usb', NULL, '" + String.format("%.0f", 12000000 * multiplier) + "', 0);",
            "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.gms.car', 0,'VideoEncoderParamsFeature__bitrate_720p_usb_hevc', NULL, '" + String.format("%.0f", 2000000 * multiplier) + "', 0);"
        };
    }

    public static final String[] BITRATE_PRE_COMMANDS = {
        "-- DELETE FROM Flags WHERE name LIKE 'VideoEncoderParamsFeature%';"
    };

    // ========== WiFi Bitrate ==========

    public static String[] buildWiFiBitrateSql(double multiplier) {
        return new String[] {
            "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.gms.car', 0,'VideoEncoderParamsFeature__bitrate_1080p_wireless', NULL, '" + String.format("%.0f", 16000000 * multiplier) + "', 0);",
            "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.gms.car', 0,'VideoEncoderParamsFeature__bitrate_1080p_wireless_hevc', NULL, '" + String.format("%.0f", 3000000 * multiplier) + "', 0);",
            "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.gms.car', 0,'VideoEncoderParamsFeature__bitrate_480p_wireless', NULL, '" + String.format("%.0f", 8000000 * multiplier) + "', 0);",
            "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.gms.car', 0,'VideoEncoderParamsFeature__bitrate_480p_wireless_hevc', NULL, '" + String.format("%.0f", 1000000 * multiplier) + "', 0);",
            "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.gms.car', 0,'VideoEncoderParamsFeature__bitrate_720p_wireless', NULL, '" + String.format("%.0f", 12000000 * multiplier) + "', 0);",
            "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.gms.car', 0,'VideoEncoderParamsFeature__bitrate_720p_wireless_hevc', NULL, '" + String.format("%.0f", 2000000 * multiplier) + "', 0);"
        };
    }

    // ========== UX Prototype ==========

    public static String[] buildUxPrototypeSql(String url) {
        return new String[] {
            "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'UxPrototype__enabled', NULL, '1', 0);",
            "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'UxPrototype__url', NULL, '" + url + "', 0);"
        };
    }

    // ========== Patched Apps ==========

    public static String[] buildPatchedAppsSql(String whiteListString) {
        return new String[] {
            "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.gms.car', 0,'app_white_list', NULL, '" + whiteListString + "', 0);",
            "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.gms.car', 0,'car_connect_broadcast_whitelist', NULL, '" + whiteListString + "', 0);",
            "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'AppValidation__allowed_package_list', NULL, '', 0);",
            "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'AppValidation__blocked_packages_by_installer', NULL, '', 0);",
            "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'AppValidation__should_bypass_validation', NULL, '1', 0);",
            "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'AppValidation__play_install_api', NULL, '0', 0);",
            "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'AppValidation__swallow_play_api_exception', NULL, '1', 0);",
            "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'AppValidation__swallow_play_api_exception_return_value', NULL, '1', 0);",
            "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.gms.car', 0,'should_bypass_validation', NULL, '1', 0);",
            "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'CarProjectionValidator__filter_disabled_packages_in_ispackageallowed_method', NULL, '0', 0);",
            "INSERT OR REPLACE INTO flag_overrides (config_package_name, type, name, account_id, value, source) VALUES ('com.google.android.projection.gearhead', 0,'UnknownSources__allow_full_screen_apps', NULL, '1', 0);",
            "-- DELETE FROM Flags WHERE name='app_black_list';",
            "-- DELETE FROM Flags WHERE name='app_white_list';"
        };
    }
}
