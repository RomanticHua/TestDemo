package com.example.myannoatationlib;


import java.io.IOException;
import java.io.Writer;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;

@SupportedAnnotationTypes("com.example.myannoatationlib.MyAnnotation")
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class MyAnnotationProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {

        StringBuilder builder = new StringBuilder();
        builder.append("package com.example.myannoatationlib.generated;\n\n")
                .append("public class JinGeneratedClass { \n")
                .append("\t public String getMessage() { \n")
                .append("\t return \"");

        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(MyAnnotation.class);
        for (Element element : elements) {
            String objType = element.getSimpleName().toString();
            builder.append(objType).append(" says hello ! ");
        }
        builder.append("\";\n") // end return
                .append("\t } \n") // close method
                .append("} \n"); // close class

        try {
            JavaFileObject sourceFile = processingEnv.getFiler().createSourceFile("com.example.myannoatationlib.generated.JinGeneratedClass");

            Writer writer = sourceFile.openWriter();
            writer.write(builder.toString());
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
}
