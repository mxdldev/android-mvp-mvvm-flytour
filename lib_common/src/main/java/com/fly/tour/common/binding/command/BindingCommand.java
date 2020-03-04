package com.fly.tour.common.binding.command;


/**
 * Description: <ViewAdapter><br>
 * Author:      mxdl<br>
 * Date:        2019/7/4<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class BindingCommand<T> {
    private BindingAction execute;

    public BindingCommand(BindingAction execute) {
        this.execute = execute;
    }

    public void execute() {
        if (execute != null) {
            execute.call();
        }
    }
}
