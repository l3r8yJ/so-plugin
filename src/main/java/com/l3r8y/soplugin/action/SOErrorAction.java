package com.l3r8y.soplugin.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import org.jetbrains.annotations.NotNull;

public class SOErrorAction extends AnAction {

  @Override
  public final void actionPerformed(@NotNull final AnActionEvent evt) {
    final Editor editor = evt.getData(CommonDataKeys.EDITOR);

  }

  @Override
  public final boolean isDumbAware() {
    return super.isDumbAware();
  }
}
