# coba_atm
Jwt Syllabus Project's ATM Simulation program.

# Installation instructions:
- After download/checkout the project you can just casually do compilation (by javac or using IDE) to generate the jar.
- Run the jar by executing: <code>java -jar app.jar</code>

## (Tips) Manually create jar using javac & jar tools:
- Set directory location: cd "your-project-dir"\coba-atm
- Compile classes: javac -sourcepath ".\src\" -d "..\test-comp\" ".\src\coba.atm.App.java"
- Generate manifest: printf "Manifest-Version: 1.0\nMain-Class: coba.atm.App\n" > ..\MANIFEST.MF
- Create jar: jar -cvmf ..\MANIFEST.MF app.jar ..\test-comp