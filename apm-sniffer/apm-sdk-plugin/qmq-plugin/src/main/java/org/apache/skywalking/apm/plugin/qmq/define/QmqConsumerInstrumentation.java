package org.apache.skywalking.apm.plugin.qmq.define;

import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.matcher.ElementMatcher;
import org.apache.skywalking.apm.agent.core.plugin.interceptor.ConstructorInterceptPoint;
import org.apache.skywalking.apm.agent.core.plugin.interceptor.InstanceMethodsInterceptPoint;
import org.apache.skywalking.apm.agent.core.plugin.interceptor.enhance.ClassInstanceMethodsEnhancePluginDefine;
import org.apache.skywalking.apm.agent.core.plugin.match.ClassMatch;

import static net.bytebuddy.matcher.ElementMatchers.named;
import static org.apache.skywalking.apm.agent.core.plugin.bytebuddy.ArgumentTypeNameMatch.takesArgumentWithType;
import static org.apache.skywalking.apm.agent.core.plugin.match.NameMatch.byName;

/**
 * @author Devonmusa
 * @date 2019/8/29 23:56
 * @since
 */
public class QmqConsumerInstrumentation extends ClassInstanceMethodsEnhancePluginDefine {
    public static final String ENHANCE_CLASS = "qunar.tc.qmq.consumer.MessageConsumerProvider";
    public static final String ENHANCE_METHOD = "";
    public static final String INTERCEPTOR_CLASS = "org.apache.skywalking.apm.plugin.qmq.QmqConsumerInterceptor";
    public static final String CONSTRUCTOR_INTERCEPT_TYPE = "org.apache.kafka.clients.consumer.ConsumerConfig";
    public static final String CONSTRUCTOR_INTERCEPTOR_CLASS = "org.apache.skywalking.apm.plugin.kafka.v1.ConsumerConstructorInterceptor";
    @Override protected ConstructorInterceptPoint[] getConstructorsInterceptPoints() {
        return new ConstructorInterceptPoint[]{
               new ConstructorInterceptPoint(){
                   @Override public ElementMatcher<MethodDescription> getConstructorMatcher() {
                       return takesArgumentWithType(0, CONSTRUCTOR_INTERCEPT_TYPE);
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
                        return named(ENHANCE_METHOD);
                    }

                    @Override public String getMethodsInterceptor() {
                        return ENHANCE_CLASS;
                    }

                    @Override public boolean isOverrideArgs() {
                        return false;
                    }
                }
        };
    }

    @Override
    protected ClassMatch enhanceClass() {
        return byName(ENHANCE_CLASS);
    }
}
