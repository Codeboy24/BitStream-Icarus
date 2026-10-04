package org.example.styles;

public class StyleHelper {

    // Main Window Layout
    public static final String ROOT_CONTAINER = 
        "-fx-background-color: #f3f3f3; " +
        "-fx-background-radius: 12; " + 
        "-fx-border-radius: 12; " +     
        "-fx-border-color: #cccccc; " +
        "-fx-border-width: 1;";

    // Search Field
    public static final String SEARCH_FIELD = 
        "-fx-background-color: white; " +
        "-fx-background-radius: 8; " +
        "-fx-border-radius: 8; " +
        "-fx-border-color: #dddddd; " +
        "-fx-padding: 8 12; " +
        "-fx-font-size: 13px; " +
        "-fx-text-fill: #333333;";

    // Dynamic Note Card Style Generator
    public static String getCardStyle(String hexColor, boolean isHovered) {
        String shadow = isHovered 
            ? "dropshadow(three-pass-box, rgba(0,0,0,0.18), 6, 0, 0, 2)"
            : "dropshadow(three-pass-box, rgba(0,0,0,0.10), 3, 0, 0, 1)";

        return String.format(
            "-fx-background-color: %s; -fx-background-radius: 12; -fx-effect: %s; -fx-cursor: hand;",
            hexColor, shadow
        );
    }

    public static final String NOTE_CARD_TITLE = 
        "-fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: #222222;";

    public static final String NOTE_CARD_PREVIEW = 
        "-fx-text-fill: #555555; -fx-font-size: 12px;";

    // Note Editor Input Styles
    public static final String EDITOR_TITLE_VALID = 
        "-fx-font-weight: bold; " +
        "-fx-font-size: 16px; " +
        "-fx-text-fill: #222222; " +
        "-fx-background-color: transparent; " +
        "-fx-prompt-text-fill: #a0a0a0;";

    public static final String EDITOR_TITLE_INVALID = 
        "-fx-font-weight: bold; " +
        "-fx-font-size: 16px; " +
        "-fx-text-fill: #e74c3c; " +
        "-fx-background-color: transparent; " +
        "-fx-prompt-text-fill: #e74c3c;";

    public static String getEditorContainerStyle(String hexColor) {
        return String.format(
            "-fx-background-color: %s; -fx-background-radius: 12; -fx-border-radius: 12; -fx-border-color: #cccccc; -fx-border-width: 1;",
            hexColor
        );
    }

    public static String getTransparentTextAreaStylesheet(String hexColor) {
        return "data:text/css," +
            ".text-area { -fx-background-color: transparent; -fx-border-color: transparent; } " +
            ".text-area .scroll-pane { -fx-background-color: transparent; } " +
            ".text-area .scroll-pane .viewport { -fx-background-color: transparent; } " +
            ".text-area .scroll-pane .content { -fx-background-color: " + hexColor + "; -fx-text-fill: #222222; }";
    }

    public static final String TRANSPARENT_SCROLL_PANE = 
        "data:text/css,.scroll-pane > .viewport { -fx-background-color: transparent; }";
}