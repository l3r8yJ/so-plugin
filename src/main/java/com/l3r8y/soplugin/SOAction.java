/*
 * The MIT License (MIT)
 * Copyright (c) 2022. Ivan Ivanchuk
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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
