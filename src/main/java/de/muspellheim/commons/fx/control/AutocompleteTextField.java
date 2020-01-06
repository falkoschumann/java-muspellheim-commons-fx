/*
 * Muspellheim Commons FX
 * Copyright (c) 2020 Falko Schumann
 */

package de.muspellheim.commons.fx.control;

import java.util.List;
import java.util.stream.Collectors;
import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import javafx.util.StringConverter;
import lombok.Getter;
import lombok.Setter;

/** Extended text field with autocomplete. The {@link #suggestionProvider} must be set. */
public class AutocompleteTextField<T> extends TextField {

  // TODO Delay suggestion search and cancel past requests

  private final ContextMenu suggestionsPopup = new ContextMenu();

  @Getter @Setter private SuggestionProvider<T> suggestionProvider;

  @Getter @Setter
  private StringConverter<T> suggestionConverter =
      new StringConverter<T>() {
        @Override
        public String toString(T object) {
          return object != null ? object.toString() : "";
        }

        @Override
        @SuppressWarnings("unchecked")
        public T fromString(String string) {
          return (T) string;
        }
      };

  @Getter @Setter private int maxSuggestions = 10;

  @Getter @Setter
  private StringConverter<T> valueConverter =
      new StringConverter<T>() {
        @Override
        public String toString(T object) {
          return object != null ? object.toString() : "";
        }

        @Override
        @SuppressWarnings("unchecked")
        public T fromString(String string) {
          return (T) string;
        }
      };

  /** Creates a new autocomplete text field */
  public AutocompleteTextField() {
    textProperty().addListener((_1, _2, userText) -> findSuggestions(userText));
    focusedProperty().addListener((_1, _2, newValue) -> suggestionsPopup.hide());
  }

  private void findSuggestions(String userText) {
    List<T> suggestions = suggestionProvider.call(userText);
    System.out.println("user text: " + userText + " -> suggestions: " + suggestions);
    if (!suggestions.isEmpty()) {
      populateSuggestions(suggestions);
      if (!suggestionsPopup.isShowing()) {
        suggestionsPopup.show(this, Side.BOTTOM, 0, 0);
      }
    } else {
      suggestionsPopup.hide();
    }
  }

  private void populateSuggestions(List<T> suggestions) {
    List<CustomMenuItem> menuItems =
        suggestions.stream()
            .limit(getMaxSuggestions())
            .map(this::createMenuItem)
            .collect(Collectors.toList());
    suggestionsPopup.getItems().setAll(menuItems);
  }

  private CustomMenuItem createMenuItem(T suggestion) {
    Label label = new Label(suggestionConverter.toString(suggestion));
    CustomMenuItem menuItem = new CustomMenuItem(label);
    menuItem.setOnAction(e -> setText(valueConverter.toString(suggestion)));
    return menuItem;
  }

  @FunctionalInterface
  public interface SuggestionProvider<T> extends Callback<String, List<T>> {}
}
