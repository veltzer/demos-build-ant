# TOFIX

Findings from a code scan on 2026-10-04.

## High

- `src/solutions/ch4/exercise-1/build.xml:4` - every solution under `src/solutions/ch4` to `ch9` (29 build files, plus `src/examples/ch04/propfile.xml`) reads `build.properties`, but none is in the repo: the fleet-shared `.gitignore:184` ignores `build.properties`. Without it all `${...}` properties stay unset; running `ant` in `ch4/exercise-1` creates a directory literally named `${build.dir}` and then fails with `srcdir ".../${src.dir}" does not exist` (verified). Commit the property files with `git add -f`, or rename them (e.g. `solution.properties`) and update the `<property file=...>` lines; do not change the shared `.gitignore`.

## Medium

- `src/examples/simple/build.xml:1` - `default="compile"` but the project only defines `build`, `clean` and `run`; a bare `ant` fails with `Target "compile" does not exist` (verified). Set the default to `build`.
- `src/examples/ch02/src/main/com/somecompany/sampleproject/HelloAnt.java:26` - loads `conf/app.properties` and `img/logo.gif` (line 29), but the example has no `src/conf` or `src/resources` and the copy steps in `build.xml` are commented out; `ant` succeeds but `run.sh` crashes with `NullPointerException: inStream parameter is null` (verified). Add the resources and re-enable the copies, or drop the resource loading.
- `src/solutions/ch7/exercise-1/build.xml:18` - copies from `common/lib` and `webmodule/output/...` (lines 21, 27), and subants into `*/build.xml`, but the exercise has no `common/` directory and `webmodule/` has no `build.xml`; the default target cannot succeed even with properties set. Add the missing modules or cut the solution down to what exists.

## Low

- `src/solutions/ch7/exercise-1/run_target_compare-src.bat:1` - `-Dsrc.archive.target=./common.v2/srcbuild.xml` fuses two tokens (presumably `./common.v2/src` and something else); fix the command line.
- `src/solutions/ch7/exercise-1/webmodule/src/main/ch7/WelcomeContextListener.java:1` - declares `package main.ch6` while living under `main/ch7`; the committed `webmodule/dest/` copy is a build output that has `main.ch7`. Fix the package and delete `dest/`.
- `src/solutions/ch6_2/exercise-2/src-dist/common.zip:1` - `src-dist/*.zip` here and in `ch7_2/exercise-2/src-dist/` are outputs of the `dist-source` target, committed without the `common/src` and `webmodule/src/main` trees they were built from. Remove the zips or add the sources.
- `src/examples/ch02/run.sh:2` - `java -jar dist/lib/*.jar` breaks once builds on two different days leave two jars (the name embeds `${DSTAMP}`, `build.xml:55`); the glob then passes the second jar as an argument. Run a fixed name or `clean` first.
- `src/examples/ch02/src/main/com/somecompany/sampleproject/HelloAnt.java:30` - `JFrame.show()` has been deprecated since Java 1.5; use `setVisible(true)`.
