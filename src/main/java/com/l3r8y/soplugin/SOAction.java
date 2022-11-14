package com.l3r8y.soplugin;

import com.intellij.ide.BrowserUtil;
import com.intellij.lang.Language;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.ui.Messages;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;

// https://stackoverflow.com/questions/ask
public class SOAction extends AnAction {

  @Override
  public final void actionPerformed(@NotNull final AnActionEvent evt) {
    final Optional<Editor> editor = Optional.ofNullable(evt.getData(CommonDataKeys.EDITOR));
    final Optional<Language> lang = Optional.ofNullable(evt.getData(CommonDataKeys.LANGUAGE));
    editor.ifPresent(
      edtr -> {
        final String code = edtr.getSelectionModel().getSelectedText();
        final Optional<String> ext = lang.map(lng -> lng.getDisplayName().toLowerCase(Locale.ROOT));
        if (ext.isPresent() && null != code) {
          Messages.showMessageDialog("Done!", "SO-Plugin", Messages.getInformationIcon());
          BrowserUtil.browse(SOAction.buildQuery(code, ext.get()));
        }
      }
    );
  }

  private static String buildQuery(final String code, final String ext) {
    return String.format(
        "https://stackoverflow.com/search?q=%s",
        URLEncoder.encode(String.format("%s [%s]", code, ext), StandardCharsets.UTF_8)
    );
  }

  @Override
  public final boolean isDumbAware() {
    return super.isDumbAware();
  }
}
