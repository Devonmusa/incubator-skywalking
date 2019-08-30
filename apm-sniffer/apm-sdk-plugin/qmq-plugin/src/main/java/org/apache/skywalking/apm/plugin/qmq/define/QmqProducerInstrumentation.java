package org.apache.skywalking.apm.plugin.qmq.define;

import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.matcher.ElementMatcher;
import org.apache.skywalking.apm.agent.core.plugin.interceptor.ConstructorInterceptPoint;
import org.apache.skywalking.apm.agent.core.plugin.interceptor.InstanceMethodsInterceptPoint;
import org.apache.skywalking.apm.agent.core.plugin.interceptor.enhance.ClassInstanceMethodsEnhancePluginDefine;
import org.apache.skywalking.apm.agent.core.plugin.match.ClassMatch;

import static org.apache.skywalking.apm.agent.core.plugin.bytebuddy.ArgumentTypeNameMatch.takesArgumentWithType;
import static org.apache.skywalking.apm.agent.core.plugin.match.NameMatch.byName;

/**
 * @author Devonmusa
 * @date 2019/8/29 23:54
 * @since
 */
public class QmqProducerInstrumentation extends ClassInstanceMethodsEnhancePluginDefine {
    public static final String ENHANCE_CLASS = "qunar.tc.qmq.producer.MessageProducerProvider";
    public static final String ENHANCE_METHOD = "";
    public static final String INTERCEPTOR_CLASS = "org.apache.skywalking.apm.plugin.qmq.QmqProducerInterceptor";
    public static final String CONSTRUCTOR_INTERCEPTOR_CLASS = "org.apache.skywalking.apm.plugin.kafka.v1.ProducerConstructorInterceptor";
    public static final String CONSTRUCTOR_INTERCEPTOR_FLAG = "org.apache.kafka.clients.producer.ProducerConfig";

    @Override protected ConstructorInterceptPoint[] getConstructorsInterceptPoints() {
        return new ConstructorInterceptPoint[]{
                new ConstructorInterceptPoint(){
                    @Override public ElementMatcher<MethodDescription> getConstructorMatcher() {
                        return takesArgumentWithType(0, CONSTRUCTOR_INTERCEPTOR_FLAG);

                    }

                    @Override public String getConstructorInterceptor() {
                        return CONSTRUCTOR_INTERCEPTOR_CLASS;
                    }
                }

        };
    }

    @Override protected InstanceMethodsInterceptPoint[] getInstanceMethodsInterceptPoints() {
        return new InstanceMethodsInterceptPoint[]{
                new InstanceMethodsInterceptPoint(){
                    @Override public ElementMatcher<MethodDescription> getMethodsMatcher() {
                        return null;
                    }

                    @Override public String getMethodsInterceptor() {
                        return INTERCEPTOR_CLASS;
                    }

                    @Override public boolean isOverrideArgs() {
                        return false;
                    }
                }
        };
    }

    @Override protected ClassMatch enhanceClass() {
        return byName(ENHANCE_CLASS);
    }
}
