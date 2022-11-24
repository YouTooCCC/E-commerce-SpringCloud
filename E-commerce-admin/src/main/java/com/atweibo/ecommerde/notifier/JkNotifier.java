package com.atweibo.ecommerde.notifier;


import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceStatusChangedEvent;
import de.codecentric.boot.admin.server.notify.AbstractEventNotifier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;


/**
 * @Description     对应用进行监控
 * @Author weibo
 * @Data 2022/11/10 10:10
 */
@Slf4j
@Component

public class JkNotifier extends AbstractEventNotifier {
    protected JkNotifier(InstanceRepository repository) {
        super(repository);
    }

    @Override
    protected Mono<Void> doNotify(InstanceEvent event, Instance instance) {
        return Mono.fromRunnable(()->{
            if(event instanceof InstanceStatusChangedEvent){
                log.info("实例状态发生变化:[{}],[{}],[{}]",instance.getRegistration().getName(),event.getInstance()
                ,((InstanceStatusChangedEvent) event).getStatusInfo().getStatus());
            }else {
                log.info("实例状态：[{}],[{}],[{}]",instance.getRegistration().getName(), event.getInstance()
                ,event.getTimestamp());
            }
        });
    }
}
