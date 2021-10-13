# BeginnerManagement
新規プレイヤーによる荒らし行為を防止するプラグインです。
>It is a plugin that prevents vandalism by new players.

# 機能詳細 | Features

* 時間制行動制限 | Time-based behavioral restrictions

初回ログイン後、Configで設定した時間行動を制限出来ます。
>After logging in for the first time, you can limit the time action set in Config.

# 制限可能項目 | List of actions that can be restricted

* ブロック破壊 | Destruction of blocks

全てのブロックの破壊を禁止します。
>Destruction of all blocks is prohibited.

* 特定のアイテムをチェスト等から取得 | Get a specific item from a chest, etc.

Configで設定したアイテムをチェストから取り出し/アイテムのドロップ/インベントリ内の移動を制限します。
>Remove items set in Config from chest / drop items / restrict movement in inventory.

* 特定のブロックを設置・破壊 | Build / destroy specific blocks.

Configで設定したブロックの建築・破壊を制限します。
>Restricts the construction and destruction of blocks set in Config.

* 特定のエンティティ(MOB等)への攻撃 | Attack on a specific entity.

Configで設定したエンティティへの攻撃を制限します。
>Limits attacks on entities set in Config.

* 特定のエンティティの設置(トロッコ等) | Attack on a specific entity.

Configで設定したエンティティへの攻撃を制限します。
>Limits attacks on entities set in Config.

* 特定のエンティティ(MOB等)への攻撃 | Attack on a specific entity.

Configで設定したエンティティへの攻撃を制限します。
>Limits attacks on entities set in Config.

* 特定のエンティティ(MOB等)への攻撃 | Attack on a specific entity.

Configで設定したエンティティへの攻撃を制限します。
>Limits attacks on entities set in Config.


# 任意プラグイン | Optional plugin

* DiscordSRV
自動リセット/バックアップ/再起動時にメッセージ送信を行えます。
>You can send messages during automatic reset, backup, and restart.

# コマンド | Commands

* /awt reset [world type(normal/nether/end)]

種別ごとにConfigに記載されたワールドを再生成します。種別がallの場合、全種別(ノーマル・ネザー・ジエンド)を再生成します。Regenerate the world described in Config for each type. If the >type is all, all types (normal, nether, and the end) will be regenerated.
* /awt backup [world name]

指定したワールドをバックアップします。
>Backs up the specified world.
* /awt reset info

自動リセット時刻の情報を表示します。
>Displays information on the automatic reset time.
* /awt restart info

自動再起動時刻の情報を表示します。
>Displays information on the automatic restart time.
* /awt backup info

自動バックアップ時刻の情報を表示します。
>Displays information on the automatic backup time.
* /awt reload

Configをリロードします。
>Reload Config.
* /awt help

コマンドガイドを表示します。
>Display the command guide.

# Permission

* autoWorldTools.admin

手動リセット・バックアップ・Configのリロードを行えます。
>You can perform manual reset, backup, and reload of Config.
* autoWorldTools.reset

手動リセットを行えます。
>You can perform a manual reset.
* autoWorldTools.backup

手動バックアップを行えます。
>You can perform a manual backup.
* autoWorldTools.resetInfo

リセット時刻情報を表示出来ます。
>The reset time information can be displayed.
* autoWorldTools.backupInfo

バックアップ時刻情報を表示出来ます。
>You can display the backup time information.

サーバー再起動時刻情報を表示出来ます。
>You can display the server restart time information.
