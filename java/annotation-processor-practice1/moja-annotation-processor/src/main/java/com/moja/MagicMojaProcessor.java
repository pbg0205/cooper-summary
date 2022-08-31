package com.moja; //package element

 import com.google.auto.service.AutoService;
 import com.squareup.javapoet.ClassName;
 import com.squareup.javapoet.JavaFile;
 import com.squareup.javapoet.MethodSpec;
 import com.squareup.javapoet.TypeSpec;

 import javax.annotation.processing.AbstractProcessor;
 import javax.annotation.processing.Filer;
 import javax.annotation.processing.Processor;
 import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
 import javax.lang.model.element.Modifier;
 import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
 import java.io.IOException;
 import java.lang.annotation.ElementType;
 import java.util.Set;

@AutoService(Processor.class)
public class MagicMojaProcessor extends AbstractProcessor { //class element

    @Override
    public Set<String> getSupportedAnnotationTypes() { //method element
        return Set.of(Magic.class.getName());
    }
    
    /**
     * - RoundEnvironment : 어노테이션 프로세서는 라운드 방식으로 코드가 동작한다.
     *      - 담당하는 라운드면 해당 로직을 처리하고 다음 라운드로 로직을 넘길 수도 있다.
     *
     */
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<? extends Element> elementsAnnotatedWith = roundEnv.getElementsAnnotatedWith(Magic.class);
        for (Element element : elementsAnnotatedWith) {
            Name elementName = element.getSimpleName();
            if (element.getKind() != ElementKind.INTERFACE) {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Magic annotation can not be used on + " + elementName);
            } else {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "Processing " + elementName);
            }

            TypeElement typeElement = (TypeElement) element;
            ClassName className = ClassName.get(typeElement);

            MethodSpec pullOut = MethodSpec.methodBuilder("pullOut")
                    .addModifiers(Modifier.PUBLIC)
                    .returns(String.class)
                    .addStatement("return $S", "Rabbit!")
                    .build();

            TypeSpec magicMoja = TypeSpec.classBuilder("MagicMoja")
                    .addModifiers(Modifier.PUBLIC)
                    .addSuperinterface(className)
                    .addMethod(pullOut)
                    .build();

            Filer filer = processingEnv.getFiler();
            try {
                JavaFile.builder(className.packageName(), magicMoja)
                        .build()
                        .writeTo(filer);
            } catch (IOException e) {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "FATAL ERROR " + e);
            }
        }

        return true; //true 반환 시, 해당 에노테이션을 처리했다는 의미
    }

}
