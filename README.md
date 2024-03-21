# Common Utilities

This is the library containing all the common utilities used across
other LDemetrios' projects. 

It is not properly tested yet, ergo version is `0.1.0`.

## Usage

```bash
git clone https://github.com/LDemetrios/LDemetriosCommons.git
gradle publish
```

(Or use `gradlew`, if gradle is not installed: `./gradlew publish` or `./gradlew.bat publish`)

## Plans

- [ ] Proper testing
- [ ] Publishing to maven central (distant future?)
- [ ] More features: 
  - [ ] More complex methods for tuples
  - [ ] Parser combinators
  - [ ] Generic `Result`s for methods attempting different approaches

## Notes

The project uses code generated with the fork of Typst, which supports output to plain text. 
Code generation process in described in Gradle task `codegen`, however, it uses binary file built for one particular OS.
Thus, it may not work for you.
That is planned to be fixed after Typst 1.0 official release, as its authors promise support of `html` output.

Besides that, you may build it yourself from `https://github.com/Myriad-Dreamin/typst/tree/text-export`
