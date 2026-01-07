import os

from PIL import Image

def map_coordinates(in1, in2, out):
    print(out)
    coord_map = Image.open(in1)
    target_img = Image.open(in2)
    img_out = coord_map.copy()
    target_pixels = target_img.load()
    pixels_out = img_out.load()
    for x in range(coord_map.size[0]):
        for y in range(coord_map.size[1]):
            coords = target_pixels[pixels_out[x, y][0]//2, pixels_out[x, y][1]//2]
            pixels_out[x, y] = (coords[0],coords[1],coords[2],coords[3])
    img_out.save(out)

def parse_dirs(in_dir, out_dir, map_name, current_dir):
    raw_map_name = map_name.removesuffix(".png")
    for file_name in os.listdir(current_dir):
        if os.path.isfile(os.path.join(current_dir, file_name)):
            src_name = os.path.join(current_dir, file_name)
            save_dir = os.path.join(out_dir, raw_map_name, os.path.relpath(current_dir, in_dir))
            dst_name = os.path.join(save_dir, file_name)
            map_coordinates(os.path.join("map", map_name), src_name, dst_name)
        elif os.path.isdir(os.path.join(current_dir, file_name)):
            new_current_dir = os.path.join(current_dir, file_name)
            new_dir = os.path.join(out_dir, raw_map_name, os.path.relpath(new_current_dir, in_dir))
            try:
                os.mkdir(new_dir)
            except FileExistsError:
                print("Directory", new_dir, "already exists.")
            parse_dirs(in_dir, out_dir, map_name, new_current_dir)


if __name__ == '__main__':
    map_dir = "map"
    in_dir = "in"
    out_dir = "out"
    for map_name in os.listdir(map_dir):
        map_subdir = map_name.removesuffix(".png")
        try:
            os.mkdir(os.path.join(out_dir, map_subdir))
        except FileExistsError:
            print("Directory", map_subdir, "already exists.")

        parse_dirs(in_dir, out_dir, map_name, in_dir)
