# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

## [v4.0.0] - 2019-11-09

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


[Unreleased]: https://github.com/falkoschumann/java-muspellheim-commons-fx/compare/v3.0.0...HEAD
[v3.0.0]: https://github.com/falkoschumann/java-muspellheim-commons-fx/compare/v2.0.0...v3.0.0
[v2.0.0]: https://github.com/falkoschumann/java-muspellheim-commons-fx/compare/v1.0.0...v2.0.0
[v1.0.0]: https://github.com/falkoschumann/java-muspellheim-commons-fx/tree/v1.0.0
