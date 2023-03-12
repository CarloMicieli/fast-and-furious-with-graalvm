insert into platforms (platform_id, platform_urn, name, manufacturer, generation, type,
                       year, release_eu, release_jp, release_na, discontinued_year, discontinued,
                       introductory_price, units_sold, media, cpu, memory,
                       display, sound, created_date, last_modified_date, version)
values ('c9d94ce1-d380-4afa-b637-fd8c3351c19f',
        'urn:platform:3do-interactive-multiplayer',
        '3DO Interactive Multiplayer',
        'Panasonic',
        5,
        'HOME_VIDEO_GAME_CONSOLE',
        1993,
        '1994-11-06',
        '1994-03-20',
        '1993-10-04',
        1996,
        true,
        699,
        2000000,
        '{"CD_ROM"}',
        '32-bit custom ARM CPU (ARM60) @ 12.5 MHz',
        '2 MB RAM, 1 MB VRAM',
        'Resolution 640×480 (interpolated), 320×240 (actual) 60 Hz for NTSC version, and 768×576 (interpolated), 384×288 (actual) 50 Hz for PAL version with either 16-bit palettized color (from 24-bit) or 24-bit truecolor',
        '16-bit stereo sound, Stereo CDDA playback, 44.1 kHz sound sampling rate',
        now(),
        now(),
        1);

insert into platforms (platform_id, platform_urn, name, manufacturer, generation, type,
                       year, release_eu, release_jp, release_na, discontinued_year, discontinued,
                       introductory_price, units_sold, media, cpu, memory,
                       display, sound, created_date, last_modified_date, version)
values ('a6003187-0363-4484-86fe-ed3f3e28e6e0',
        'urn:platform:atari-2600',
        'Atari 2600',
        'Atari',
        2,
        'HOME_VIDEO_GAME_CONSOLE',
        1977,
        '1978-01-01',
        '1983-10-01',
        '1977-09-01',
        1992,
        true,
        189.95,
        30000000,
        '{"ROM_CARTRIDGE"}',
        '8-bit MOS Technology 6507 @ 1.19 MHz',
        '128 bytes RAM',
        'Television Interface Adaptor',
        'Via Television Interface Adaptor',
        now(),
        now(),
        1);

insert into platforms (platform_id, platform_urn, name, manufacturer, generation, type,
                       year, release_eu, release_jp, release_na, discontinued_year, discontinued,
                       introductory_price, units_sold, media, cpu, memory,
                       display, sound, created_date, last_modified_date, version)
values ('3a96e4b4-b65c-4de0-a325-e98564f57493',
        'urn:platform:atari-5200',
        'Atari 5200',
        'Atari',
        2,
        'HOME_VIDEO_GAME_CONSOLE',
        1982,
        NULL,
        NULL,
        '1982-11-01',
        1984,
        true,
        NULL,
        1000000,
        '{"ROM_CARTRIDGE"}',
        'MOS 6502C @ 1.79 MHz',
        '16 KB RAM',
        '14 modes: Six text modes (8×8, 4×8, and 8×10 character matrices supported), Eight graphics modes including 80 pixels per line (16 color), 160 pixels per line (4 color), 320 pixels per line (2 color),[30] variable height and width up to overscan 384×240 pixels',
        'Via Television Interface Adaptor',
        now(),
        now(),
        1);

insert into platforms (platform_id, platform_urn, name, manufacturer, generation, type,
                       year, release_eu, release_jp, release_na, discontinued_year, discontinued,
                       introductory_price, units_sold, media, cpu, memory,
                       display, sound, created_date, last_modified_date, version)
values ('88aaea1d-faad-4f5b-830b-f091d23c6083',
        'urn:platform:atari-7800',
        'Atari 7800',
        'Atari',
        3,
        'HOME_VIDEO_GAME_CONSOLE',
        1986,
        '1987-01-01',
        NULL,
        '1986-05-01',
        1992,
        true,
        140,
        1000000,
        '{"ROM_CARTRIDGE"}',
        'Atari SALLY @ 1.19-1.79 MHz',
        '4 KB RAM, 4 KB BIOS ROM, 48 KB cartridge ROM space',
        '160×240, 320×240 (288 vertical for PAL), 25 colors out of 256',
        'TIA as used in the 2600 for video and sound. In 7800 mode it is only used for sound.',
        now(),
        now(),
        1);

insert into platforms (platform_id, platform_urn, name, manufacturer, generation, type,
                       year, release_eu, release_jp, release_na, discontinued_year, discontinued,
                       introductory_price, units_sold, media, cpu, memory,
                       display, sound, created_date, last_modified_date, version)
values ('64607640-3821-49a3-b3cd-e8f2554ca8eb',
        'urn:platform:atari-jaguar',
        'Atari Jaguar',
        'Atari',
        5,
        'HOME_VIDEO_GAME_CONSOLE',
        1993,
        '1994-06-27',
        '1994-12-08',
        '1993-11-23',
        1996,
        true,
        249.99,
        150000,
        '{"ROM_CARTRIDGE"}',
        'Motorola 68000, 2 dissimilar custom RISC processors',
        '2 MB RAM',
        NULL,
        'CD-quality sound (16-bit stereo)',
        now(),
        now(),
        1);

insert into platforms (platform_id, platform_urn, name, manufacturer, generation, type,
                       year, release_eu, release_jp, release_na, discontinued_year, discontinued,
                       introductory_price, units_sold, media, cpu, memory,
                       display, sound, created_date, last_modified_date, version)
values ('63b25f4c-bbdc-4a1a-a733-5fdd25d6ab9e',
        'urn:platform:atari-jaguar-cd',
        'Atari Jaguar CD',
        'Atari',
        5,
        'VIDEO_GAME_CONSOLE_PERIPHERAL',
        1993,
        NULL,
        NULL,
        '1995-09-21',
        1996,
        true,
        149.95,
        NULL,
        '{"CD_ROM"}',
        NULL,
        NULL,
        NULL,
        NULL,
        now(),
        now(),
        1);

insert into platforms (platform_id, platform_urn, name, manufacturer, generation, type,
                       year, release_eu, release_jp, release_na, discontinued_year, discontinued,
                       introductory_price, units_sold, media, cpu, memory,
                       display, sound, created_date, last_modified_date, version)
values ('cacbc983-6aa4-42c8-89d8-ff17cc4433c7',
        'urn:platform:nec-pc-engine',
        'NEC PC Engine',
        'NEC',
        4,
        'HOME_VIDEO_GAME_CONSOLE',
        1987,
        '1989-11-22',
        '1987-10-30',
        '1989-08-29',
        1994,
        true,
        NULL,
        7000000,
        '{"HU_CARD", "CD_ROM"}',
        'Hudson Soft HuC6280 @ 7.16 MHz',
        '8 KB RAM, 64 KB VRAM',
        'Composite or RF TV out; 565×242 or 256×239, 512 color palette, 482 colors on-screen',
        'HuC6280, PSG, 5 to 10 bit stereo PCM',
        now(),
        now(),
        1);

insert into platforms (platform_id, platform_urn, name, manufacturer, generation, type,
                       year, release_eu, release_jp, release_na, discontinued_year, discontinued,
                       introductory_price, units_sold, media, cpu, memory,
                       display, sound, created_date, last_modified_date, version)
values ('2c0776ca-bbb9-4c04-b3a3-da514aca9f21',
        'urn:platform:nintendo-64',
        'Nintendo 64',
        'Nintendo',
        5,
        'HOME_VIDEO_GAME_CONSOLE',
        1996,
        '1997-03-01',
        '1996-06-23',
        '1996-09-26',
        2002,
        true,
        200,
        32930000,
        '{"ROM_CARTRIDGE"}',
        '64-bit NEC VR4300 @ 93.75 MHz',
        '4 MB Rambus RDRAM (8 MB with Expansion Pak)',
        NULL,
        '16-bit, 48 or 44.1 kHz stereo',
        now(),
        now(),
        1);

insert into platforms (platform_id, platform_urn, name, manufacturer, generation, type,
                       year, release_eu, release_jp, release_na, discontinued_year, discontinued,
                       introductory_price, units_sold, media, cpu, memory,
                       display, sound, created_date, last_modified_date, version)
values ('f0aa6823-622c-40c0-ace0-132e406f1344',
        'urn:platform:nintendo-game-boy',
        'Nintendo Game Boy',
        'Nintendo',
        4,
        'HANDHELD_GAME_CONSOLE',
        1989,
        '1990-09-28',
        '1989-04-21',
        '1989-07-31',
        2003,
        true,
        89.99,
        118690000,
        '{"ROM_CARTRIDGE"}',
        'Sharp LR35902 core @ 4.19 MHz',
        '64 KB RAM',
        'STN LCD 160 × 144 pixels, 47 × 43 mm (w × h)',
        '2 pulse wave generators, 1 PCM 4-bit wave sample (64 4-bit samples played in 1×64 bank or 2×32 bank) channel, 1 noise generator, and one audio input from the cartridge',
        now(),
        now(),
        1);

insert into platforms (platform_id, platform_urn, name, manufacturer, generation, type,
                       year, release_eu, release_jp, release_na, discontinued_year, discontinued,
                       introductory_price, units_sold, media, cpu, memory,
                       display, sound, created_date, last_modified_date, version)
values ('c749e46c-4f7a-46e3-a136-6b09c9d7228f',
        'urn:platform:nintendo-gamecube',
        'Nintendo GameCube',
        'Nintendo',
        6,
        'HOME_VIDEO_GAME_CONSOLE',
        2001,
        '2002-05-03',
        '2001-09-14',
        '2001-11-18',
        2007,
        true,
        199,
        21740000,
        '{"GAME_CUBE_GAME_DISC"}',
        '32-bit IBM PowerPC 750CXe Gekko @ 486 MHz',
        '24 MB of 1T-SRAM @ 324 MHz as system RAM, 3 MB of embedded 1T-SRAM as video RAM, 16 MB of DRAM as I/O buffer RAM',
        NULL,
        'Analog Stereo',
        now(),
        now(),
        1);

insert into platforms (platform_id, platform_urn, name, manufacturer, generation, type,
                       year, release_eu, release_jp, release_na, discontinued_year, discontinued,
                       introductory_price, units_sold, media, cpu, memory,
                       display, sound, created_date, last_modified_date, version)
values ('0b542aa9-e9c8-43d9-abde-d40e72b2a20b',
        'urn:platform:nintendo-nes',
        'Nintendo NES',
        'Nintendo',
        3,
        'HOME_VIDEO_GAME_CONSOLE',
        1983,
        '1986-09-01',
        '1983-07-15',
        '1985-10-18',
        1995,
        true,
        179,
        61910000,
        '{"ROM_CARTRIDGE"}',
        'Ricoh 2A03/2A07 @ 1.79/1.66 MHz',
        '24 MB of 1T-SRAM @ 324 MHz as system RAM, 3 MB of embedded 1T-SRAM as video RAM, 16 MB of DRAM as I/O buffer RAM',
        NULL,
        'Analog Stereo',
        now(),
        now(),
        1);

insert into platforms (platform_id, platform_urn, name, manufacturer, generation, type,
                       year, release_eu, release_jp, release_na, discontinued_year, discontinued,
                       introductory_price, units_sold, media, cpu, memory,
                       display, sound, created_date, last_modified_date, version)
values ('087f22fb-166e-49b0-b2bf-96fdc5d06e15',
        'urn:platform:nintendo-snes',
        'Nintendo SNES',
        'Nintendo',
        3,
        'HOME_VIDEO_GAME_CONSOLE',
        1990,
        '1992-04-11',
        '1990-11-21',
        '1991-08-23',
        2005,
        true,
        199,
        49100000,
        '{"ROM_CARTRIDGE"}',
        'Ricoh 5A22 @ 3.58 MHz',
        '128 KB RAM, 64 KB VRAM',
        NULL,
        'Nintendo S-SMP',
        now(),
        now(),
        1);

insert into platforms (platform_id, platform_urn, name, manufacturer, generation, type,
                       year, release_eu, release_jp, release_na, discontinued_year, discontinued,
                       introductory_price, units_sold, media, cpu, memory,
                       display, sound, created_date, last_modified_date, version)
values ('43aa1feb-cdf2-4a7c-a8f7-d41dfda4b2c9',
        'urn:platform:sega-32x',
        'SEGA 32x',
        'SEGA',
        5,
        'VIDEO_GAME_CONSOLE_PERIPHERAL',
        1994,
        '1994-12-01',
        '1994-12-03',
        '1994-11-21',
        1996,
        true,
        159.99,
        800000,
        '{"ROM_CARTRIDGE", "CD_ROM"}',
        '2× SH-2 32-bit RISC @ 23 MHz',
        '256 KB RAM, 256 KB VRAM',
        '320 × 240 resolution, 32,768 on-screen colors',
        NULL,
        now(),
        now(),
        1);

insert into platforms (platform_id, platform_urn, name, manufacturer, generation, type,
                       year, release_eu, release_jp, release_na, discontinued_year, discontinued,
                       introductory_price, units_sold, media, cpu, memory,
                       display, sound, created_date, last_modified_date, version)
values ('84411542-8121-4142-b0c4-44091cb49e66',
        'urn:platform:sega-dreamcast',
        'SEGA Dreamcast',
        'SEGA',
        6,
        'HOME_VIDEO_GAME_CONSOLE',
        1998,
        '1999-10-14',
        '1998-11-27',
        '1999-09-09',
        2001,
        true,
        199,
        9130000,
        '{"GD_ROM", "CD_ROM", "MINI_CD"}',
        'Hitachi SH-4 32-bit RISC @ 200 MHz',
        '16 MB RAM, 8 MB video RAM, 2 MB audio RAM',
        NULL,
        '67 MHz Yamaha AICA with 32-bit ARM7 RISC CPU core, 64 channels',
        now(),
        now(),
        1);

insert into platforms (platform_id, platform_urn, name, manufacturer, generation, type,
                       year, release_eu, release_jp, release_na, discontinued_year, discontinued,
                       introductory_price, units_sold, media, cpu, memory,
                       display, sound, created_date, last_modified_date, version)
values ('41c96fd7-2e84-462e-a612-887feafd7923',
        'urn:platform:sega-game-gear',
        'SEGA Game Gear',
        'SEGA',
        4,
        'HANDHELD_GAME_CONSOLE',
        1990,
        '1991-04-01',
        '1990-10-06',
        '1991-04-01',
        1997,
        true,
        149.99,
        10620000,
        '{"ROM_CARTRIDGE"}',
        'Zilog Z80 @ 3.5 MHz',
        '8 KB RAM, 16 KB VRAM',
        '160 × 144 pixel resolution, 4096-color palette, 32 colors on-screen',
        'Texas Instruments SN76489, Mono speaker, Headphone jack',
        now(),
        now(),
        1);

insert into platforms (platform_id, platform_urn, name, manufacturer, generation, type,
                       year, release_eu, release_jp, release_na, discontinued_year, discontinued,
                       introductory_price, units_sold, media, cpu, memory,
                       display, sound, created_date, last_modified_date, version)
values ('1d23a565-5738-471e-8628-b1cfe9b91612',
        'urn:platform:sega-master-system',
        'SEGA Master System',
        'SEGA',
        3,
        'HOME_VIDEO_GAME_CONSOLE',
        1985,
        '1987-06-01',
        '1985-10-20',
        '1986-09-01',
        1991,
        true,
        200,
        10000000,
        '{"ROM_CARTRIDGE"}',
        'Zilog Z80A @ 3.58 MHz (NTSC)',
        '8 KB RAM, 16 KB VRAM',
        '256 × 192 resolution, 32 colors on-screen',
        'Yamaha VDP PSG (SN76489), Yamaha YM2413',
        now(),
        now(),
        1);

insert into platforms (platform_id, platform_urn, name, manufacturer, generation, type,
                       year, release_eu, release_jp, release_na, discontinued_year, discontinued,
                       introductory_price, units_sold, media, cpu, memory,
                       display, sound, created_date, last_modified_date, version)
values ('f3c85317-4b03-411e-8170-b9d161e91675',
        'urn:platform:sega-mega-drive',
        'SEGA Mega Drive',
        'SEGA',
        4,
        'HOME_VIDEO_GAME_CONSOLE',
        1988,
        '1990-09-01',
        '1988-10-29',
        '1989-08-14',
        1997,
        true,
        199,
        30750000,
        '{"ROM_CARTRIDGE"}',
        'Motorola 68000 @ 7.6 MHz, Zilog Z80 @ 3.58 MHz',
        '64 KB RAM, 64 KB VRAM, 8 KB audio RAM',
        '256×224 (NTSC) or 320×240, 256×240 (PAL) pixels, 512 color palette, 61 colors on-screen, Interlaced: 320×448, 256×448 (NTSC) or 320×480, 256×480 (PAL)',
        'Yamaha YM2612, Texas Instruments SN76489',
        now(),
        now(),
        1);

insert into platforms (platform_id, platform_urn, name, manufacturer, generation, type,
                       year, release_eu, release_jp, release_na, discontinued_year, discontinued,
                       introductory_price, units_sold, media, cpu, memory,
                       display, sound, created_date, last_modified_date, version)
values ('98059bf2-a240-4f79-a02a-8686a15a5b60',
        'urn:platform:sega-megacd',
        'SEGA MegaCD',
        'SEGA',
        4,
        'VIDEO_GAME_CONSOLE_PERIPHERAL',
        1991,
        '1993-04-01',
        '1991-12-12',
        '1992-10-15',
        1996,
        true,
        299,
        2240000,
        '{"CD_ROM"}',
        'Motorola 68000 @ 12.5 MHz',
        '6 Mbit RAM (programs, pictures, and sounds), 128 Kbit RAM (CD-ROM cache), 64 kbit RAM (backup memory)',
        'custom ASIC',
        'Ricoh RF5C164',
        now(),
        now(),
        1);

insert into platforms (platform_id, platform_urn, name, manufacturer, generation, type,
                       year, release_eu, release_jp, release_na, discontinued_year, discontinued,
                       introductory_price, units_sold, media, cpu, memory,
                       display, sound, created_date, last_modified_date, version)
values ('a9588454-70ff-4e22-89c1-1e7275ffd0b5',
        'urn:platform:sega-saturn',
        'SEGA Saturn',
        'SEGA',
        5,
        'HOME_VIDEO_GAME_CONSOLE',
        1994,
        '1995-07-08',
        '1994-11-22',
        '1995-05-11',
        2000,
        true,
        399,
        9260000,
        '{"CD_ROM", "MINI_CD"}',
        '2× Hitachi SH-2 @ 28.6 MHz',
        '2 MB RAM, 1.5 MB VRAM, 512 KB sound RAM, expandable with Extended RAM Cartridge',
        'VDP1 & VDP2 video display processors',
        'Yamaha YMF292',
        now(),
        now(),
        1);

insert into platforms (platform_id, platform_urn, name, manufacturer, generation, type,
                       year, release_eu, release_jp, release_na, discontinued_year, discontinued,
                       introductory_price, units_sold, media, cpu, memory,
                       display, sound, created_date, last_modified_date, version)
values ('ca491885-5b08-4c3b-beb0-ed158c982c5f',
        'urn:platform:snk-neo-geo-pocket',
        'SNK Neo-Geo Pocket',
        'SNK',
        5,
        'HANDHELD_GAME_CONSOLE',
        1998,
        NULL,
        '1998-10-28',
        NULL,
        1999,
        true,
        NULL,
        NULL,
        '{"ROM_CARTRIDGE"}',
        'Toshiba TLCS900H @ 6.144 MHz',
        NULL,
        'Virtual screen 256×256, 16 palettes per plane, 64 sprites per frame',
        'Z80 & SN76489 compatible',
        now(),
        now(),
        1);

insert into platforms (platform_id, platform_urn, name, manufacturer, generation, type,
                       year, release_eu, release_jp, release_na, discontinued_year, discontinued,
                       introductory_price, units_sold, media, cpu, memory,
                       display, sound, created_date, last_modified_date, version)
values ('2f08afd1-af7e-4231-9555-f079a36f5667',
        'urn:platform:snk-neo-geo-pocket-color',
        'SNK Neo-Geo Pocket Color',
        'SNK',
        5,
        'HANDHELD_GAME_CONSOLE',
        1999,
        '1999-10-01',
        '1999-03-16',
        '1999-08-06',
        2001,
        true,
        69.95,
        NULL,
        '{"ROM_CARTRIDGE"}',
        'Toshiba TLCS900H core (16-bit) @ 6.144 MHz, Zilog Z80 @ 3.072 MHz for sound',
        '12 KB RAM for 900/H, 4 KB RAM for Z80, 64 KB ROM',
        '2.7", 160x152 resolution, 146 colors on screen out of a palette of 4096',
        'T6W28 (enhanced SN76489), 8-bit DACs',
        now(),
        now(),
        1);

insert into platforms (platform_id, platform_urn, name, manufacturer, generation, type,
                       year, release_eu, release_jp, release_na, discontinued_year, discontinued,
                       introductory_price, units_sold, media, cpu, memory,
                       display, sound, created_date, last_modified_date, version)
values ('0e310165-ffb5-456b-825c-5efece4a52f5',
        'urn:platform:snk-neo-geo-aes',
        'SNK Neo Geo AES',
        'SNK',
        4,
        'HOME_VIDEO_GAME_CONSOLE',
        1993,
        NULL,
        NULL,
        NULL,
        2007,
        true,
        NULL,
        1000000,
        '{"ROM_CARTRIDGE"}',
        'Motorola 68000 @ 12MHz, Zilog Z80A @ 4MHz',
        '64KB RAM, 84KB VRAM, 2KB Sound Memory',
        '320×224 resolution, 4096 on-screen colors out of a palette of 65536',
        'Yamaha YM2610',
        now(),
        now(),
        1);


insert into platforms (platform_id, platform_urn, name, manufacturer, generation, type,
                       year, release_eu, release_jp, release_na, discontinued_year, discontinued,
                       introductory_price, units_sold, media, cpu, memory,
                       display, sound, created_date, last_modified_date, version)
values ('ff380835-bbc9-42bc-bd62-27349de29393',
        'urn:platform:snk-neo-geo-mvs',
        'SNK Neo Geo MVS',
        'SNK',
        4,
        'ARCADE_SYSTEM_BOARD',
        1990,
        '1991-01-01',
        '1990-04-26',
        '1990-08-22',
        2007,
        true,
        649.99,
        1180000,
        '{"ROM_CARTRIDGE"}',
        'Motorola 68000 @ 12MHz, Zilog Z80A @ 4MHz',
        '64KB RAM, 84KB VRAM, 2KB Sound Memory',
        '320×224 resolution, 4096 on-screen colors out of a palette of 65536',
        'Yamaha YM2610',
        now(),
        now(),
        1);

insert into platforms (platform_id, platform_urn, name, manufacturer, generation, type,
                       year, release_eu, release_jp, release_na, discontinued_year, discontinued,
                       introductory_price, units_sold, media, cpu, memory,
                       display, sound, created_date, last_modified_date, version)
values ('335a4341-5a0f-4940-bc98-84ef9e3a8a1e',
        'urn:platform:snk-neo-geo-cd',
        'SNK Neo Geo CD',
        'SNK',
        4,
        'HOME_VIDEO_GAME_CONSOLE',
        1995,
        '1994-12-03',
        '1994-09-09',
        '1996-01-15',
        1997,
        true,
        399,
        NULL,
        '{"CD_ROM"}',
        'Motorola 68000 @ 12 MHz',
        '7 MB RAM',
        '320×224 resolution, 4096 on-screen colors out of a palette of 65536',
        NULL,
        now(),
        now(),
        1);
