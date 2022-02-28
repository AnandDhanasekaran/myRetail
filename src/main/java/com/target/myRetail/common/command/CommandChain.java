package com.target.myRetail.common.command;

import com.target.myRetail.common.CommandContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class CommandChain {
//  public static final Boolean STOP = true;
//  public static final Boolean VALID = true;
  public static final Boolean CONTINUE = false;
  @Autowired
  private ApplicationContext applicationContext;

  public void execute(List<Class> commandClasses, CommandContext commandContext) throws Exception {
    List<ACommand> commands = new ArrayList();
    Iterator classIterator = commandClasses.iterator();

    ACommand command;
    while(classIterator.hasNext()) {
      Class classes = (Class)classIterator.next();
      command = this.getCommand(classes);
      if (command != null) {
        commands.add(command);
      }
    }

    Iterator commandIterator = commands.iterator();

    while(commandIterator.hasNext()) {
      command = (ACommand)commandIterator.next();
      boolean stop = this.handleCommand(command, commandContext);
      if (stop) {
        break;
      }
    }

  }

  ACommand getCommand(Class it) {
    ACommand command;
    try {
      command = (ACommand)this.applicationContext.getBean(it);
    } catch (Exception e) {
      command = (ACommand)this.applicationContext.getBean(it.getSimpleName());
    }

    return command;
  }

  void execute(Class commandName, CommandContext commandContext) throws Exception {
    ACommand command = (ACommand)this.applicationContext.getBean(commandName);
    if (command != null) {
      this.handleCommand(command, commandContext);
    }

  }

  private boolean handleCommand(ACommand command, CommandContext commandContext) throws Exception {
    Boolean conditionResult = false;
    Boolean stop = false;

    try {
      conditionResult = command.evaluate(commandContext);
      stop = command.preProcess(conditionResult, commandContext);
      if (!stop) {
        stop = command.execute(conditionResult, commandContext);
      }

      if (!stop) {
        stop = command.postProcess(conditionResult, commandContext);
      }
    } catch (Exception e) {
      if (!stop) {
        stop = command.handleException(e, conditionResult, commandContext);
      }
    }

    return stop;
  }
}
