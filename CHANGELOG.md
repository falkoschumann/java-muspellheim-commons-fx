# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

## [v5.0.0] - 2019-12-19

### Added

*   `Hint` a specialized tooltip showing a hint for an node, like a text field.
*   Re-added `StatusBar` with message, progress bar and optional left and right
    items.

### Removed

*   Dependency to ControlsFX.

## [v4.11.0] - 2019-12-08

### Added

*   `DataTooltipBuilder` can install tooltip as hover.

### Changed

*   Simplify calls to `DispatchQueue`.

### Fixed

*   `ScalableAxis` scaled with wrong offset.

## [v4.10.0] - 2019-12-05

### Added

*   Mouse drag move single axis, when both axes are scalable.

## [v4.9.0] - 2019-12-03

### Change

*   Scroll 2 axis separately.

## [v4.8.0] - 2019-11-28

### Changed

*   Scalable axis can zoom and move a horizontal or vertical value axis. 

## [v4.7.0] - 2019-11-28

### Added

*   Synchronously testing mode for dispatch queue.
*   Scalable horizontal number axis.  

## [v4.6.0] - 2019-11-21

### Added

*   Simple pareto chart based on bar chart. 

## [v4.5.0] - 2019-11-20

### Added

*   `DataTooltipBuilder` optionally apply a title to tooltip.

### Changed

*   Optimised axis tick values of `LongAxis` and `DateAxis`.

## [v4.4.0] - 2019-11-18

### Added

*   Formattable tick label for Date and date time axis.  

### Fixed

*   Date and date time axis did not shown ticks if auto range disabled.

## [v4.3.0] - 2019-11-17

### Added

*   Tooltip builder for name/value pairs.
*   Long axis based on `ValueAxis`.
*   Date and date time axis.

## [v4.2.1] - 2019-11-14

### Fixed

*   Maven artefact depended on OpenJFX 11.

## [v4.2.0] - 2019-11-13

### Changed

*   All resources bundled in one properties file `messages.proerties`.

## [v4.1.0] - 2019-11-12

### Added

*   Dispatch queue for running tasks in application thread or background thread. 

## [v4.0.1] - 2019-11-10

### Fixed

*   Required dependencies should be `api` dependencies, not `implementation`.

## [v4.0.0] - 2019-11-10

### Added

*   Validation decoration with a hint popup.

### Changed

*   Main class of demo app is now `de.muspellheim.commons.fx.demo.App`. 
*   Demo app uses `StageController`. 
*   Replaced `ExceptionDialog` with `CustomExceptionDialog` extends ControlsFX
    `ExceptionDialog`.

### Removed

*   Status bar. ControlsFX has a status bar.

## [v3.2.0] - 2019-11-05

### Added

*   Table cells and cell factories for `LocalDate` and `LocalDateTime`.

### Fixed

*   Root node of `DateIntervalPicker` and `StatusBar` should not focusable.

## [v3.1.0] - 2019-11-04

### Added

*   Demo showing controls and dialogs by starting main class
    `de.muspellheim.commons.fx.demo.DemoApp`.

### Fixed

*   Date interval picker value was not initialized before control is shown.

## [v3.0.0] - 2019-11-03

### Added

*   Status bar control.

### Changed

*   Renamed date interval picker.
*   Date interval picker base on Control.

### Fixed

*   About dialog can not close.

## [v2.0.0] - 2019-10-31

### Added

*   Date picker control.
*   View controller factory will load resource bundle if it exists.

### Changed

*   Added sub packages and moved some classes to new packages.
*   Compensated different generic types order of `StageController` and
    `ViewControllerFactory`.
*   Replaced about dialog.

## [v1.0.0] - 2019-10-27

### Added

*   General about dialog.
*   General exception dialog.
*   View controller factory.
*   Stage controller based on view controller.


[Unreleased]: https://github.com/falkoschumann/java-muspellheim-commons-fx/compare/v4.10.0...HEAD
[v4.10.0]: https://github.com/falkoschumann/java-muspellheim-commons-fx/compare/v4.9.0...v4.10.0
[v4.9.0]: https://github.com/falkoschumann/java-muspellheim-commons-fx/compare/v4.8.0...v4.9.0
[v4.8.0]: https://github.com/falkoschumann/java-muspellheim-commons-fx/compare/v4.7.0...v4.8.0
[v4.7.0]: https://github.com/falkoschumann/java-muspellheim-commons-fx/compare/v4.6.0...v4.7.0
[v4.6.0]: https://github.com/falkoschumann/java-muspellheim-commons-fx/compare/v4.5.0...v4.6.0
[v4.5.0]: https://github.com/falkoschumann/java-muspellheim-commons-fx/compare/v4.4.0...v4.5.0
[v4.4.0]: https://github.com/falkoschumann/java-muspellheim-commons-fx/compare/v4.3.0...v4.4.0
[v4.3.0]: https://github.com/falkoschumann/java-muspellheim-commons-fx/compare/v4.2.1...v4.3.0
[v4.2.1]: https://github.com/falkoschumann/java-muspellheim-commons-fx/compare/v4.2.0...v4.2.1
[v4.2.0]: https://github.com/falkoschumann/java-muspellheim-commons-fx/compare/v4.1.0...v4.2.0
[v4.1.0]: https://github.com/falkoschumann/java-muspellheim-commons-fx/compare/v4.0.1...v4.1.0
[v4.0.1]: https://github.com/falkoschumann/java-muspellheim-commons-fx/compare/v4.0.0...v4.0.1
[v4.0.0]: https://github.com/falkoschumann/java-muspellheim-commons-fx/compare/v3.2.0...v4.0.0
[v3.2.0]: https://github.com/falkoschumann/java-muspellheim-commons-fx/compare/v3.1.0...v3.2.0
[v3.1.0]: https://github.com/falkoschumann/java-muspellheim-commons-fx/compare/v3.0.0...v3.1.0
[v3.0.0]: https://github.com/falkoschumann/java-muspellheim-commons-fx/compare/v2.0.0...v3.0.0
[v2.0.0]: https://github.com/falkoschumann/java-muspellheim-commons-fx/compare/v1.0.0...v2.0.0
[v1.0.0]: https://github.com/falkoschumann/java-muspellheim-commons-fx/tree/v1.0.0
