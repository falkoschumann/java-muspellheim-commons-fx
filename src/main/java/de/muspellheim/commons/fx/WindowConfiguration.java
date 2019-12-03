/*
 * Muspellheim Commons FX
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.commons.fx;

import lombok.RequiredArgsConstructor;
import lombok.Value;

/** The configuration of a stage. */
@Value
@RequiredArgsConstructor(staticName = "of")
@SuppressWarnings("checkstyle:VisibilityModifier")
public class WindowConfiguration {

  /**
   * The horizontal location of a window on the screen.
   *
   * @return the horizontal window location
   */
  int x;

  /**
   * The vertical location of a window on the screen.
   *
   * @return the vertical window location
   */
  int y;

  /**
   * The width of a window.
   *
   * @return the window width
   */
  int width;

  /**
   * The height of a window.
   *
   * @return the window height
   */
  int height;
}
