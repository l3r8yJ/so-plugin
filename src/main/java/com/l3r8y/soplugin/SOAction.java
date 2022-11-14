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
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Consumer;
import org.jetbrains.annotations.NotNull;

public class SOAction extends AnAction {

  private static final String PLAIN_TEXT = "plain text";

  @Override
  public final void actionPerformed(@NotNull final AnActionEvent evt) {
    final Optional<Language> lang = Optional.ofNullable(evt.getData(CommonDataKeys.LANGUAGE));
    Optional.ofNullable(
      evt.getData(CommonDataKeys.EDITOR)
    ).ifPresent(editor -> SOAction.openBrowserWhenCodeSelected(lang, editor));
  }

  @Override
  public final boolean isDumbAware() {
    return super.isDumbAware();
  }

  private static void openBrowserWhenCodeSelected(final Optional<Language> lang, final Editor edtr) {
    final String code = edtr.getSelectionModel().getSelectedText();
    lang
      .map(lng -> lng.getDisplayName().toLowerCase(Locale.ROOT))
      .ifPresent(SOAction.openBrowser(code));
  }

  private static Consumer<String> openBrowser(final String code) {
    return language -> {
      if (SOAction.isLanguageSupported(language) && SOAction.isCodeSelected(code)) {
        BrowserUtil.browse(SOAction.queryFrom(code, language));
      }
    };
  }

  private static boolean isLanguageSupported(final String lang) {
    if (SOAction.PLAIN_TEXT.equalsIgnoreCase(lang)) {
      new SOActionMessageDialog("File extension not supported.").show();
      return false;
    }
    return true;
  }

  private static boolean isCodeSelected(final String code) {
    if (null == code) {
      new SOActionMessageDialog("Code not selected, please do a selection.").show();
      return false;
    }
    new SOActionMessageDialog("Done!").show();
    return true;
  }

  private static String queryFrom(final String code, final String ext) {
    return String.format(
      "https://stackoverflow.com/search?q=%s",
      URLEncoder.encode(String.format("%s [%s]", code, ext), StandardCharsets.UTF_8)
    );
  }
}
